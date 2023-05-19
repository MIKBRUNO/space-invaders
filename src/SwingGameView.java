import game.view.Actor;
import game.view.GameView;
import game.view.Scene;
import swing_view.BgColorScene;
import swing_view.SwingActor;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.floor;

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
        SwingScene = (BgColorScene) scene;
        setBackground(SwingScene.getBgColor());
    }

    private BgColorScene SwingScene;
    private final int tileSize = 48;
    private final int screenCols = 16;
    private final int screenRows = 12;
    private final int screenWidth = screenCols * tileSize;
    private final int screenHeight = screenRows * tileSize;
}
