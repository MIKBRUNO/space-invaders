package swing_view;

import game.view.Actor;

import java.awt.*;

public interface SwingActor extends Actor {
    void paintActor(Graphics g, float widthRatio, float heightRatio);
}
