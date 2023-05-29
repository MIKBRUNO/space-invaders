package game;

import game.engine.Arena;
import game.engine.ArenaActor;
import game.engine.controller.ControllerObserver;
import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;
import swing_view.SwingBullet;

public class Guardian extends ArenaActor implements ControllerObserver<GuardianControllerEvent> {
    @Override
    public LivingSubscriber<Guardian> getLivingInstance() {
        return livingSubscriber;
    }

    public Guardian(Arena arena, Location location, Bounds bounds) {
        super(arena, location, bounds);
    }

    @Override
    public void eventTick(float deltaSeconds) {
        ArActorLocation = ArActorLocation.plusV(deltaSeconds, Speed);
        super.eventTick(deltaSeconds);
    }

    @Override
    public void eventOnOverlap(OverlappingObject other) {
        System.out.println(this + " overlapped by " + other);
    }

    @Override
    public void update(GuardianControllerEvent param) {
        final float speed = 1.e-3f;
        Speed = param.vector().mul(speed);
        if (param.fire()) {
            SwingBullet bullet = new SwingBullet(SelfArena, ArActorLocation, new Bounds(0.01f, 0.01f));
            SelfArena.register(bullet.getLivingInstance());
        }
    }

    private Vector Speed = new Vector(0, 0);

    private final LivingSubscriber<Guardian> livingSubscriber = new LivingSubscriber<>(this);
}
