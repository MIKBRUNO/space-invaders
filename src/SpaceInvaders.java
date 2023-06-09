import game.GameState;
import game.SIContext;
import game.ScoreCounter;
import game.enemies.*;
import game.engine.ActorRegister;
import game.engine.EventTickListener;
import game.engine.GameLoop;
import game.engine.overlapping.OverlapFactor;
import game.engine.overlapping.OverlapManager;
import game.engine.smart_register.SmartSubscribing;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;
import game.engine.view.Scene;
import game.engine.view.ViewManager;
import game.guardian.Guardian;
import swing_view.SwingViewManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpaceInvaders extends SmartSubscribing<GameInfoListener> {
    public SpaceInvaders(JPanel frame, ScoreCounter scoreCounter) {
        SIScoreCounter = scoreCounter;
        frame.add(SIViewManager.SwingPanel);
        frame.addKeyListener(SIController.keyListener);
        refreshScene();
    }

    public synchronized void start() {
        SILoop.start();
    }

    private final SmartSubscribing<Enemy> smartSubscribingForEnemies = new SmartSubscribing<Enemy>() {
        @Override
        protected synchronized void innerRegister(Enemy enemy) {
            Enemies.add(enemy);
        }

        @Override
        public synchronized void onDeathCallback(Enemy deadSubscriber) {
            Enemies.remove(deadSubscriber);
        }
    };

    public synchronized void refreshScene() {
        // destroy old
        if (CurrentGuardian != null) {
            CurrentGuardian.getLivingInstance().destroy();
            CurrentGuardian = null;
        }
        if (collectiveMind != null) {
            collectiveMind.getLivingInstance().destroy();
            collectiveMind = null;
        }
        for (var enemy : Enemies) {
            enemy.getLivingInstance().destroy();
        }
        Enemies.clear();
        // create new
        CurrentGuardian = new Guardian(
                Context, new Location(3 * TILE_SIZE, 11 * TILE_SIZE),
                TILE_BOUNDS, GUARDIAN_SPEED
        );
        SIController.register(CurrentGuardian.getLivingInstance());
        SISpawner.registerOverlappingActor(CurrentGuardian.getLivingInstance(), OverlapFactor.GUARDIAN);

        Vector stepLeft = new Vector(-TILE_SIZE * 0.5f, 0);
        Vector stepDown = new Vector(0, TILE_SIZE);
        Location location = new Location(TILE_SIZE * 2, TILE_SIZE * 2);
        for (int col = 0; col < COLS; ++col) {
            for (int i = 0; i < UFO_ROWS; ++i) {
                smartSubscribingForEnemies.register(new Ufo(
                        Context, location.plus(TILE_SIZE * col, TILE_SIZE * (i + CRAB_ROWS + OCTOPUS_ROWS)),
                        TILE_BOUNDS, stepLeft, stepDown
                ).getLivingInstance());
            }
            for (int i = 0; i < CRAB_ROWS; ++i) {
                smartSubscribingForEnemies.register(new Crab(
                        Context, location.plus(TILE_SIZE * col, TILE_SIZE * (i + OCTOPUS_ROWS)),
                        TILE_BOUNDS, stepLeft, stepDown
                ).getLivingInstance());
            }
            for (int i = 0; i < OCTOPUS_ROWS; ++i) {
                smartSubscribingForEnemies.register(new Octopus(
                        Context, location.plus(TILE_SIZE * col, TILE_SIZE * i),
                        TILE_BOUNDS, stepLeft, stepDown
                ).getLivingInstance());
            }
        }
        for (var enemy : Enemies) {
            SISpawner.registerOverlappingActor(enemy.getLivingInstance(), OverlapFactor.ENEMY);
        }
        collectiveMind = new CollectiveMind(
                2f, Context, location.plus(-TILE_SIZE / 2f, 0),
                new Bounds(TILE_SIZE * COLS, TILE_SIZE * (CRAB_ROWS + UFO_ROWS + OCTOPUS_ROWS)),
                Enemies,
                stepLeft,
                stepDown
        );
        SILoop.register(collectiveMind.getLivingInstance());
    }
    private Guardian CurrentGuardian = null;
    private final List<Enemy> Enemies = new CopyOnWriteArrayList<>();
    private CollectiveMind collectiveMind = null;

    private synchronized void onStateChanged(GameState state) {
        switch (state) {
            case DEATH -> {
                Lives -= 1;
                if (InfoListener != null) {
                    InfoListener.livesUpdate(Lives);
                }
                if (Lives <= 0) {
                    Lives = 3;
                    if (InfoListener != null) {
                        InfoListener.livesUpdate(Lives);
                    }
                    Context.setGameState(GameState.LOSE);
                }
            }
            case WIN -> {
                SIScoreCounter.commitScore();
                refreshScene();
            }
            case RUNNING -> {
                // 🗿🚬
            }
            case LOSE -> {
                SIScoreCounter.commitScore();
                SIScoreCounter.setScore(0);
                Lives = 3;
                if (InfoListener != null) {
                    InfoListener.livesUpdate(Lives);
                }
                refreshScene();
            }
        }
    }
    private int Lives = 3;

    private final int COLS = 11;
    private final int UFO_ROWS = 2;
    private final int CRAB_ROWS = 2;
    private final int OCTOPUS_ROWS = 1;
    private static final int TILE_SIZE = 48;
    private final Bounds TILE_BOUNDS = new Bounds(TILE_SIZE, TILE_SIZE / 2f);
    private final float GUARDIAN_SPEED = TILE_SIZE * 5;
    private final Bounds BULLET_BOUNDS = new Bounds(3, 12);
    private static final int SCREEN_COLS = 16;
    private static final int SCREEN_ROWS = 12;
    public static final int SCREEN_WIDTH = SCREEN_COLS * TILE_SIZE;
    public static final int SCREEN_HEIGHT = SCREEN_ROWS * TILE_SIZE;
    public final SIContext Context = new SIContext() {
        @Override
        public void setGameState(GameState state) {
            synchronized (Context) {
                SIGameState = state;
            }
            onStateChanged(state);
        }

        @Override
        public Bounds getBulletBounds() {
            return BULLET_BOUNDS;
        }

        @Override
        public float getGuardianSpeed() {
            return GUARDIAN_SPEED;
        }

        @Override
        public synchronized ScoreCounter getScoreCounter() {
            return SIScoreCounter;
        }

        @Override
        public synchronized OverlapManager getOverlapManager() {
            return SIOverlapManager;
        }

        @Override
        public synchronized SmartSubscribing<EventTickListener> getEventTickController() {
            return getLoop();
        }

        @Override
        public synchronized ViewManager getViewManager() {
            return SIViewManager;
        }

        @Override
        public synchronized Scene getScene() {
            return SIScene;
        }

        @Override
        public synchronized ActorRegister getSpawner() {
            return SISpawner;
        }

        @Override
        public synchronized GameLoop getLoop() {
            return SILoop;
        }
    };
    private GameState SIGameState = GameState.RUNNING;
    private final ScoreCounter SIScoreCounter;
    private final OverlapManager SIOverlapManager = new OverlapManager();
    private final SwingViewManager SIViewManager = new SwingViewManager(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final ActorRegister SISpawner = new ActorRegister(Context);
    private final GameLoop SILoop = new GameLoop(Context);
    private final Bounds SceneBounds = new Bounds((float) SCREEN_WIDTH, (float) SCREEN_HEIGHT);
    private final Scene SIScene = () -> SceneBounds;
    private final SwingController SIController = new SwingController();

    private GameInfoListener InfoListener = null;
    @Override
    protected synchronized void innerRegister(GameInfoListener gameInfoListener) {
        InfoListener = gameInfoListener;
        InfoListener.livesUpdate(Lives);
    }

    @Override
    public synchronized void onDeathCallback(GameInfoListener deadSubscriber) {
        InfoListener = null;
    }
}
