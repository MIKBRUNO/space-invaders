package swing_view;

import game.engine.actors.Actor;

import java.awt.*;

import static java.lang.Math.round;

public class SwingGuardianView implements SwingActorView {
    @Override
    public void paintActor(Graphics g, float widthRatio, float heightRatio) {
        g.setColor(Color.WHITE);
        g.fillRect(
                round(widthRatio * MyActor.getLocation().x()),
                round(heightRatio * MyActor.getLocation().y()),
                round(widthRatio * MyActor.getBounds().width()),
                round(heightRatio * MyActor.getBounds().height())
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
}
