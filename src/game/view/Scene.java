package game.view;

import game.Bounds;

public interface Scene {
    Bounds getBounds();
    Iterable<Actor> getActorIterator();
}
