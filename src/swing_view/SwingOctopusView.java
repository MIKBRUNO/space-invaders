package swing_view;

import game.engine.actors.Actor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static java.lang.Math.round;

public class SwingOctopusView implements SwingActorView {
    public SwingOctopusView() {
        try {
            URL url = SwingOctopusView.class.getResource("octopus-0.png");
            if (url == null) {
                throw new RuntimeException("octopus sprite not found");
            }
            IMG = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException("octopus sprite not found");
        }
    }

    @Override
    public void paintActor(Graphics g, float widthRatio, float heightRatio) {
        g.setColor(Color.WHITE);
        g.drawImage(
                IMG,
                round(widthRatio * MyActor.getLocation().x()),
                round(heightRatio * MyActor.getLocation().y()),
                round(widthRatio * MyActor.getBounds().width()),
                round(heightRatio * MyActor.getBounds().height()),
                null
        );
    }

    @Override
    public Actor getActor() {
        return MyActor;
    }

    @Override
    public void setActor(Actor actor) {
        MyActor = actor;
    }

    private Actor MyActor = null;
    private final BufferedImage IMG;
}
