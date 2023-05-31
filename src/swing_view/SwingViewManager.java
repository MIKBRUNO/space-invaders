package swing_view;

import game.engine.GameSession;
import game.engine.view.ActorView;
import game.engine.view.Scene;
import game.engine.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class SwingViewManager extends ViewManager {
    public SwingViewManager() {
        SwingPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        SwingPanel.setBackground(Color.BLACK);
        SwingPanel.setDoubleBuffered(true);

        try (InputStream in = SwingViewManager.class.getResourceAsStream("conf.txt")) {
            ActorViewFactory.setClassMap(in);
        } catch (IOException e) {
            throw new RuntimeException("bad config file");
        }

    }

    public void paintCallback(Graphics g) {

    }

    public final JPanel SwingPanel = new JPanel() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (Context == null) {
                return;
            }
            Scene SwingScene = Context.getScene();
            getActorViews().forEach((ActorView i) -> {
                SwingActorView ra = (SwingActorView) i;
                float wRatio = screenWidth / SwingScene.getBounds().width();
                float hRatio = screenHeight / SwingScene.getBounds().height();
                ra.paintActor(g, wRatio, hRatio);
            });
            paintCallback(g);
        }
    };

    @Override
    public void update(GameSession.GameContext context) {
        Context = context;
        SwingPanel.repaint();
    }

    protected GameSession.GameContext Context = null;
    protected final int tileSize = 48;
    protected final int screenCols = 16;
    protected final int screenRows = 12;
    protected final int screenWidth = screenCols * tileSize;
    protected final int screenHeight = screenRows * tileSize;
}
