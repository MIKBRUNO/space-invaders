package game.engine.actors;

import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.view.Scene;

public abstract class ArenaActor implements OverlappingActor {
    public abstract LivingSubscriber<? extends ArenaActor> getLivingInstance();

    public ArenaActor(Scene arena, Location location, Bounds bounds) {
        SelfArena = arena;
        ArActorLocation = location;
        ArActorBounds = bounds;
    }

    @Override
    public final synchronized Location getLocation() {
        return ArActorLocation;
    }

    @Override
    public final synchronized Bounds getBounds() {
        return ArActorBounds;
    }

    protected final synchronized void setLocation(Location location) {
        ArActorLocation = location;
    }

    protected final synchronized void setBounds(Bounds bounds) {
        ArActorBounds = bounds;
    }

    /*
    * by default this method checks if ArenaActor went out of Arena bounds
    * and then places it to the (I hope) place in Arena
    * so to override it appropriately it is obligatory to place super's method to the end
    */
    @Override
    public synchronized void eventTick(float deltaSeconds) {
        if (ArActorLocation.y() < 0) {
            ArActorLocation = ArActorLocation.plus(
                    0,
                    0 - ArActorLocation.y()
            );
        }
        if (ArActorLocation.y() + ArActorBounds.height() > SelfArena.getBounds().height()) {
            ArActorLocation = ArActorLocation.plus(
                    0,
                    SelfArena.getBounds().height() - ArActorLocation.y() - ArActorBounds.height()
            );
        }
        if (ArActorLocation.x() < 0) {
            ArActorLocation = ArActorLocation.plus(
                    0 - ArActorLocation.x(),
                    0
            );
        }
        if (ArActorLocation.x() + ArActorBounds.width() > SelfArena.getBounds().width()) {
            ArActorLocation = ArActorLocation.plus(
                    SelfArena.getBounds().width() - ArActorLocation.x() - ArActorBounds.width(),
                    0
            );
        }
    }

    @Override
    public synchronized Location getOverlapLocation() {
        return ArActorLocation;
    }

    @Override
    public synchronized Bounds getOverlapBounds() {
        return ArActorBounds;
    }

    private final Scene SelfArena;

    private Location ArActorLocation;

    private Bounds ArActorBounds;
}
