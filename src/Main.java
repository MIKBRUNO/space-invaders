import game.engine.EventTickListener;
import game.engine.smart_register.LivingSubscriber;
import game.engine.types.Bounds;
import swing_view.SwingViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setUndecorated(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.isControlDown() && e.getExtendedKeyCode() == KeyEvent.VK_W) {
                    System.exit(0);
                }
            }
        });

        SwingController gameController = new SwingController();
        frame.addKeyListener(gameController.keyListener);

        final List<Float> delta_sec = new ArrayList<>( List.of(1f) );

        EventTickListener tickListener = ds -> delta_sec.set(0, ds);
        SwingViewManager gameView = new SwingViewManager() {
            @Override
            public void paintCallback(Graphics g) {
//                g.setColor(Color.WHITE);
//                g.drawString("FPS: " + 1/delta_sec.get(0), 20, 20);
            }
        };
        frame.add(gameView.SwingPanel);
        frame.pack();

        SpaceInvaders gameSession = new SpaceInvaders(
                gameController, gameView,
                new Bounds(1, (float)gameView.SwingPanel.getHeight()/gameView.SwingPanel.getWidth())
        );

        LivingSubscriber<EventTickListener> ls = new LivingSubscriber<>(tickListener);
        gameSession.Context.getEventTickController().register(ls);

        frame.setFocusable(true);
        frame.setVisible(true);
        gameSession.run();
    }
}
