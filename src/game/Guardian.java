package game;

import game.engine.GameSession;
import game.engine.controller.ControllerObserver;
import game.engine.overlapping.OverlapFactor;
import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

public class Guardian extends ContextualizedArenaActor implements ControllerObserver<GuardianControllerEvent> {
    public Guardian(GameSession.GameContext context, Location location, Bounds bounds) {
        super(context, location, bounds);
    }

    @Override
    public synchronized LivingSubscriber<Guardian> getLivingInstance() {
        return livingSubscriber;
    }

    @Override
    public synchronized void eventTick(float deltaSeconds) {
        setLocation(getLocation().plusV(deltaSeconds, Speed));
        super.eventTick(deltaSeconds);
    }

    @Override
    public synchronized void eventOnOverlap(OverlappingObject other) {
//        System.out.println(this + " overlapped by " + other);
    }

    @Override
    public synchronized void controllerUpdate(GuardianControllerEvent param) {
        final float speed = 1.e-3f;
        Speed = param.vector().mul(speed);
        if (param.fire()) {
            Bullet bullet = new Bullet(Context, getLocation(), new Bounds(0.01f, 0.01f));
            Context.getSpawner().registerOverlappingActor(bullet.getLivingInstance(), OverlapFactor.BULLET);
        }
    }

    private Vector Speed = new Vector(0, 0);

    private final LivingSubscriber<Guardian> livingSubscriber = new LivingSubscriber<>(this);
}
