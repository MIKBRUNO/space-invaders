package game.engine;

import game.engine.smart_register.SmartSubscribing;
import game.engine.types.Bounds;
import game.engine.view.Actor;
import game.engine.view.Scene;

import java.util.ArrayList;

public class Arena extends SmartSubscribing<ArenaActor> implements Scene {
    public Arena(Bounds bounds) {
        ArenaBounds = bounds;
    }

    @Override
    protected void innerRegister(ArenaActor arenaActor) {
        Actors.add(arenaActor);
    }

    @Override
    public void onDeathCallback(ArenaActor deadSubscriber) {
        Actors.remove(deadSubscriber);
    }

    @Override
    public Bounds getBounds() {
        return ArenaBounds;
    }

    @Override
    public Iterable<Actor> getActorIterator() {
        return Actors.stream().map((arenaActor) -> arenaActor.actorInstance).toList();
    }

    private final Bounds ArenaBounds;

    private final ArrayList<ArenaActor> Actors = new ArrayList<>();
}
