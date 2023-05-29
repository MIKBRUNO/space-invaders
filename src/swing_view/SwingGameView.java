package swing_view;

import game.engine.Arena;
import game.engine.view.Actor;
import game.engine.view.GameView;
import game.engine.view.Scene;

import javax.swing.*;
import java.awt.*;

public class SwingGameView extends JPanel implements GameView {
    public SwingGameView() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Actor i : SwingScene.getActorIterator()) {
            SwingActor ra = (SwingActor) i;
            float wRatio = screenWidth / SwingScene.getBounds().width();
            float hRatio = screenHeight / SwingScene.getBounds().height();
            ra.paintActor(g, wRatio, hRatio);
        }
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void setScene(Scene scene) {
        SwingScene = scene;
    }

    private Scene SwingScene;
    private final int tileSize = 48;
    private final int screenCols = 16;
    private final int screenRows = 12;
    private final int screenWidth = screenCols * tileSize;
    private final int screenHeight = screenRows * tileSize;
}
