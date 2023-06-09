package game.enemies;

import game.ContextualizedArenaActor;
import game.SIContext;
import game.engine.actors.ArenaActor;
import game.engine.overlapping.OverlapFactor;
import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

public class EnemyBullet extends ContextualizedArenaActor {
    public EnemyBullet(SIContext context, Location location, Bounds bounds, float speed) {
        super(context, location, bounds);
        Speed = new Vector((float) Math.random(), speed);
    }

    @Override
    public LivingSubscriber<? extends ArenaActor> getLivingInstance() {
        return livingSubscriber;
    }
    private final LivingSubscriber<EnemyBullet> livingSubscriber = new LivingSubscriber<>(this);

    @Override
    public void eventOnOverlap(OverlappingObject other, OverlapFactor tag) {
        if (tag != OverlapFactor.ENEMY) {
            livingSubscriber.destroy();
        }
    }

    @Override
    public synchronized void eventTick(float deltaSeconds) {
        setLocation(getLocation().plusV(deltaSeconds, Speed));
        if (getLocation().y() > Context.getScene().getBounds().height()) {
            livingSubscriber.destroy();
        }
    }
    private final Vector Speed;
}
