package game;

import game.view.Actor;
import game.view.Scene;

import java.util.ArrayList;

public class ArrayListScene implements Scene {
    @Override
    public Bounds getBounds() {
        return ArenaBounds;
    }

    @Override
    public Iterable<Actor> getActorIterator() {
        return ArenaActors;
    }

    public ArrayListScene(Bounds bounds) {
        ArenaBounds = bounds;
    }

    public ArrayList<Actor> getActors() {
        return ArenaActors;
    }

    private final Bounds ArenaBounds;
    private final ArrayList<Actor> ArenaActors = new ArrayList<>(64);
}
