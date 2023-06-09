package swing_view;

import game.engine.GameContext;
import game.engine.overlapping.OverlapFactor;
import game.engine.view.ActorView;
import game.engine.view.Scene;
import game.engine.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class SwingViewManager extends ViewManager {
    public SwingViewManager(int screenWidth, int screenHeight) {
        ScreenWidth = screenWidth;
        ScreenHeight = screenHeight;
        SwingPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        SwingPanel.setBackground(Color.BLACK);
        SwingPanel.setDoubleBuffered(true);

        try (InputStream in = SwingViewManager.class.getResourceAsStream("conf.txt")) {
            if (in == null) {
                throw new RuntimeException("bad config file");
            }
            ActorViewFactory.setClassMap(in);
        } catch (IOException e) {
            throw new RuntimeException("bad config file");
        }

    }

    public void paintCallback(Graphics g) {
//        g.setColor(Color.WHITE);
//        g.drawString("Overlapping bullets: " + Context.getOverlapManager().OverlapParts.get(OverlapFactor.BULLET).size(), 100, 100);
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
                float wRatio = ScreenWidth / SwingScene.getBounds().width();
                float hRatio = ScreenHeight / SwingScene.getBounds().height();
                ra.paintActor(g, wRatio, hRatio);
            });
            paintCallback(g);
        }
    };

    @Override
    public void update(GameContext context) {
        Context = context;
        SwingPanel.repaint();
    }

    protected GameContext Context = null;

    private final int ScreenWidth;
    private final int ScreenHeight;
}
