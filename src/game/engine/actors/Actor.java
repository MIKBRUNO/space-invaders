package game.engine.actors;

import game.engine.types.Bounds;
import game.engine.types.Location;

public interface Actor {
    Location getLocation();

    Bounds getBounds();
}
