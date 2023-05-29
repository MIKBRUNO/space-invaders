package swing_view;

import game.engine.Arena;
import game.Guardian;
import game.engine.types.Bounds;
import game.engine.types.Location;

import java.awt.*;

import static java.lang.Math.round;

public class SwingGuardian extends Guardian implements SwingActor {
    public SwingGuardian(Arena arena, Location location, Bounds bounds) {
        super(arena, location, bounds);
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
