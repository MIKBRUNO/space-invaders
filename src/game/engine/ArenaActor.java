package game.engine;

import game.engine.overlapping.OverlappingObject;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.view.Actor;

public abstract class ArenaActor implements OverlappingObject, EventTickListener {
    public abstract LivingSubscriber<? extends ArenaActor> getLivingInstance();

    public Actor getActorInstance() {
        return actorInstance;
    }

    public ArenaActor(Arena arena, Location location, Bounds bounds) {
        SelfArena = arena;
        ArActorLocation = location;
        ArActorBounds = bounds;
    }

    /*
    * by default this method checks if ArenaActor went out of Arena bounds
    * and then places it to the (I hope) place in Arena
    * so to override it appropriately place super's method to the end
    */
    @Override
    public void eventTick(float deltaSeconds) {
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
    public void eventOnOverlap(OverlappingObject other) {

    }

    @Override
    public Location getOverlapLocation() {
        return ArActorLocation;
    }

    @Override
    public Bounds getOverlapBounds() {
        return ArActorBounds;
    }

    protected final Arena SelfArena;

    protected Location ArActorLocation;

    protected Bounds ArActorBounds;

    private final Actor actorInstance = new Actor() {
        @Override
        public Location getLocation() {
            return ArActorLocation;
        }

        @Override
        public Bounds getBounds() {
            return ArActorBounds;
        }
    };
}
