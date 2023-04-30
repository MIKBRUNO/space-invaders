package game;

public interface Actor {
    Location getLocation();
    Bounds getBounds();
    void setLocation(Location location);
    void setBounds(Bounds bounds);
}
