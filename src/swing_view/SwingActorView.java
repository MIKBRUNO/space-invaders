package swing_view;

import game.engine.view.ActorView;

import java.awt.*;

public interface SwingActorView extends ActorView {
    void paintActor(Graphics g, float widthRatio, float heightRatio);
}
