package game.guardian;

import game.ContextualizedArenaActor;
import game.SIContext;
import game.engine.GameContext;
import game.engine.overlapping.OverlapFactor;
import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

public class Bullet extends ContextualizedArenaActor {
    public Bullet(SIContext context, Location location, Bounds bounds, float speed) {
        super(context, location, bounds);
        Speed = new Vector(0, -speed);
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
    public synchronized void eventOnOverlap(OverlappingObject other, OverlapFactor tag) {
        if (tag != OverlapFactor.GUARDIAN) {
            livingSubscriber.destroy();
        }
    }

    private final LivingSubscriber<Bullet> livingSubscriber = new LivingSubscriber<>(this);

    private final Vector Speed;
}
