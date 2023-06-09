package game.engine;

import game.engine.actors.Actor;
import game.engine.actors.MovingActor;
import game.engine.actors.OverlappingActor;
import game.engine.overlapping.OverlapFactor;
import game.engine.smart_register.LivingSubscriber;

public class ActorRegister {
    public ActorRegister(GameContext context) {
        Context = context;
    }

    public void registerActor(LivingSubscriber<? extends Actor> actor) {
        Context.getViewManager().register(actor);
    }

    public void registerMovingActor(LivingSubscriber<? extends MovingActor> actor) {
        Context.getViewManager().register(actor);
        Context.getEventTickController().register(actor);
    }

    public void registerOverlappingActor(LivingSubscriber<? extends OverlappingActor> actor, OverlapFactor tag) {
        Context.getViewManager().register(actor);
        Context.getEventTickController().register(actor);
        Context.getOverlapManager().registerOverlappingObject(actor, tag);
    }

    private final GameContext Context;
}
