package game;

import game.engine.Arena;
import game.engine.ArenaActor;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

public class Bullet extends ArenaActor {
    public Bullet(Arena arena, Location location, Bounds bounds) {
        super(arena, location, bounds);
    }

    @Override
    public LivingSubscriber<Bullet> getLivingInstance() {
        return livingSubscriber;
    }

    @Override
    public void eventTick(float deltaSeconds) {
        ArActorLocation = ArActorLocation.plusV(deltaSeconds, Speed);
//        super.eventTick(deltaSeconds);
        if (ArActorLocation.y() < 0) {
            livingSubscriber.destroy();
        }
    }

    private final LivingSubscriber<Bullet> livingSubscriber = new LivingSubscriber<>(this);

    private final Vector Speed = new Vector(0, 1.e-3f);
}
