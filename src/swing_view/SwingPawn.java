package swing_view;

import game.Bounds;
import game.Location;
import game.Pawn;

import java.awt.*;

import static java.lang.Math.floor;
import static java.lang.Math.round;

public class SwingPawn extends Pawn implements SwingActor {
    public SwingPawn(Location location, Bounds bounds) {
        setLocation(location);
        setBounds(bounds);
    }
    @Override
    public void paintActor(Graphics g, float widthRatio, float heightRatio) {
        g.setColor(Color.WHITE);
        g.fillRect(
                round(widthRatio * getLocation().x()),
                round(heightRatio * getLocation().y()),
                round(widthRatio * getBounds().width()),
                round(heightRatio * getBounds().height())
        );
    }
}
