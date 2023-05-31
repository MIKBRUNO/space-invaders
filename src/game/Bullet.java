package game;

import game.engine.GameSession;
import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

public class Bullet extends ContextualizedArenaActor {
    public Bullet(GameSession.GameContext context, Location location, Bounds bounds) {
        super(context, location, bounds);
    }

    @Override
    public synchronized LivingSubscriber<Bullet> getLivingInstance() {
        return livingSubscriber;
    }

    @Override
    public synchronized void eventTick(float deltaSeconds) {
        setLocation(getLocation().plusV(deltaSeconds, Speed));
        if (getLocation().y() < 0) {
            livingSubscriber.destroy();
        }
    }

    @Override
    public synchronized void eventOnOverlap(OverlappingObject other) {  }

    private final LivingSubscriber<Bullet> livingSubscriber = new LivingSubscriber<>(this);

    private final Vector Speed = new Vector(0, -1.e-3f);
}
