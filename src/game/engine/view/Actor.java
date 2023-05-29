package game.engine.view;

import game.engine.types.Bounds;
import game.engine.types.Location;

public interface Actor<Appearance extends ActorAppearance> {
    Location getLocation();
    Bounds getBounds();
    Appearance getAppearance();
}
