package game.enemies;

import game.ContextualizedArenaActor;
import game.GameState;
import game.SIContext;
import game.ScoreCounter;
import game.engine.controller.ControllerObserver;
import game.engine.overlapping.OverlapFactor;
import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;
import game.guardian.Bullet;
import game.guardian.Guardian;

public abstract class Enemy extends ContextualizedArenaActor implements ControllerObserver<CollectiveOrder> {
    public Enemy(SIContext context, Location location, Bounds bounds, Vector leftStep, Vector downStep) {
        super(context, location, bounds);
        LeftStep = leftStep;
        DownStep = downStep;
    }

    @Override
    public synchronized Bounds getOverlapBounds() {
        return getBounds().mulS(2f/3f, 1);
    }

    public abstract int getScorePoints();

    @Override
    public synchronized LivingSubscriber<Enemy> getLivingInstance() {
        return livingSubscriber;
    }

    private final LivingSubscriber<Enemy> livingSubscriber = new LivingSubscriber<>(this);

    @Override
    public synchronized void eventOnOverlap(OverlappingObject other, OverlapFactor tag) {
        if (tag == OverlapFactor.BULLET) {
            ScoreCounter score = Context.getScoreCounter();
            score.setScore(score.getScore() + getScorePoints());
            livingSubscriber.destroy();
        }
        else if (tag == OverlapFactor.GUARDIAN) {
            Context.setGameState(GameState.LOSE);
        }
    }

    @Override
    public synchronized void controllerUpdate(CollectiveOrder param) {
        switch (param) {
            case STEP_DOWN -> setLocation(getLocation().plusV(1, DownStep));
            case STEP_LEFT -> setLocation(getLocation().plusV(1, LeftStep));
            case STEP_RIGHT -> setLocation(getLocation().plusV(-1, LeftStep));
        }
    }

    private final Vector LeftStep;
    private final Vector DownStep;

    @Override
    public synchronized void eventTick(float deltaSeconds) {
        if (getLocation().y() + getBounds().height() > Context.getScene().getBounds().height()) {
            Context.setGameState(GameState.LOSE);
        }
        elapsedTime += deltaSeconds;
        if (elapsedTime > randomDelta) {
            elapsedTime = 0;
            EnemyBullet bullet = new EnemyBullet(
                    Context, getLocation().plus(getBounds().width() / 2, 0),
                    Context.getBulletBounds(), Context.getGuardianSpeed()
            );
            Context.getSpawner().registerOverlappingActor(bullet.getLivingInstance(), OverlapFactor.ENEMY_BULLET);
        }
    }
    private float elapsedTime = 0;
    private final float randomDelta = (float) (Math.random() * 10) + 2;
}
