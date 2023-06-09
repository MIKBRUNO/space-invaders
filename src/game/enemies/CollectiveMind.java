package game.enemies;

import game.GameState;
import game.SIContext;
import game.engine.EventTickListener;
import game.engine.smart_register.LivingSubscriber;
import game.engine.smart_register.SmartSubscribing;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectiveMind implements EventTickListener {
    public synchronized LivingSubscriber<CollectiveMind> getLivingInstance() {
        return LivingInstance;
    }
    private final LivingSubscriber<CollectiveMind> LivingInstance = new LivingSubscriber<>(this);

    public CollectiveMind(float delta, SIContext context,
                          Location location, Bounds bounds,
                          List<Enemy> enemies,
                          Vector left, Vector down
    ) {
        DELTA = delta;
        Context = context;
        LeftStep = left;
        DownStep = down;
        CollectiveLocation = location;
        CollectiveBounds = bounds;
        Enemies = enemies;
    }

    @Override
    public synchronized void eventTick(float deltaSeconds) {
        if (Enemies.isEmpty()) {
            Context.setGameState(GameState.WIN);
        }
        elapsedDelta += deltaSeconds;
        if (elapsedDelta > DELTA) {
            elapsedDelta = 0;
            if (
                    CollectiveLocation.x() < 0
                    || CollectiveLocation.x() + CollectiveBounds.width() >= Context.getScene().getBounds().width()
            ) {
                Direction = -Direction;
                CollectiveLocation = CollectiveLocation.plusV(1f, DownStep);
                CollectiveOrder order = CollectiveOrder.STEP_DOWN;
                for (var enemy : Enemies) {
                    enemy.controllerUpdate(order);
                }
                CollectiveLocation = CollectiveLocation.plusV((float) Direction, LeftStep);
                order = Direction > 0 ? CollectiveOrder.STEP_LEFT : CollectiveOrder.STEP_RIGHT;
                for (var enemy : Enemies) {
                    enemy.controllerUpdate(order);
                }
            }
            else {
                CollectiveLocation = CollectiveLocation.plusV((float) Direction, LeftStep);
                CollectiveOrder order = Direction > 0 ? CollectiveOrder.STEP_LEFT : CollectiveOrder.STEP_RIGHT;
                for (var enemy : Enemies) {
                    enemy.controllerUpdate(order);
                }
            }
        }
    }

    private Location CollectiveLocation;
    private final Bounds CollectiveBounds;
    private final Vector LeftStep;
    private final Vector DownStep;
    private int Direction = 1;
    private final SIContext Context;
    private final List<Enemy> Enemies;
    private final float DELTA;
    private float elapsedDelta = 0;
}
