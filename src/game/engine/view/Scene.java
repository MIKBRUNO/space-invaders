package game.engine.view;

import game.engine.types.Bounds;

public interface Scene {
    Bounds getBounds();
    Iterable<Actor> getActorIterator();
}
