package game.guardian;

import game.ContextualizedArenaActor;
import game.GameState;
import game.SIContext;
import game.engine.GameContext;
import game.engine.controller.ControllerObserver;
import game.engine.overlapping.OverlapFactor;
import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

public class Guardian extends ContextualizedArenaActor implements ControllerObserver<GuardianControllerEvent> {
    public Guardian(SIContext context, Location location, Bounds bounds, float speed) {
        super(context, location, bounds);
        SpeedValue = speed;
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
    public synchronized void eventOnOverlap(OverlappingObject other, OverlapFactor tag) {
        if (tag == OverlapFactor.ENEMY_BULLET) {
            Context.setGameState(GameState.DEATH);
        }
    }

    @Override
    public synchronized void controllerUpdate(GuardianControllerEvent param) {
        Speed = param.vector().mul(SpeedValue);
        if (param.fire()) {
            Bullet bullet = new Bullet(
                    Context,
                    getLocation().plus(getBounds().width() / 2, 0),
                    new Bounds(24, 4), SpeedValue * 2
            );
            Context.getSpawner().registerOverlappingActor(bullet.getLivingInstance(), OverlapFactor.BULLET);
        }
    }

    private Vector Speed = new Vector(0, 0);
    private final float SpeedValue;
    private final LivingSubscriber<Guardian> livingSubscriber = new LivingSubscriber<>(this);
}
