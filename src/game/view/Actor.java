package game.view;

import game.Bounds;
import game.Location;

public interface Actor {
    Location getLocation();
    Bounds getBounds();
    void setLocation(Location location);
    void setBounds(Bounds bounds);
}
