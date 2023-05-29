package swing_view;

import game.Bullet;
import game.engine.Arena;
import game.engine.types.Bounds;
import game.engine.types.Location;

import java.awt.*;

import static java.lang.Math.round;

public class SwingBullet extends Bullet implements SwingActor {
    public SwingBullet(Arena arena, Location location, Bounds bounds) {
        super(arena, location, bounds);
    }

    @Override
    public void paintActor(Graphics g, float widthRatio, float heightRatio) {
        g.setColor(Color.GREEN);
        g.fillRect(
                round(widthRatio * getLocation().x()),
                round(heightRatio * getLocation().y()),
                round(widthRatio * getBounds().width()),
                round(heightRatio * getBounds().height())
        );
    }
}
