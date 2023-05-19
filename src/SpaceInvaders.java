import game.Bounds;
import game.GameObject;
import game.GameSession;
import game.Location;
import swing_view.BlackScreenScene;
import swing_view.SwingPawn;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpaceInvaders {
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

        SwingController gameController = new SwingController((e) -> {});
        frame.addKeyListener(gameController.keyListener);

        SwingGameView gameView = new SwingGameView();
        frame.add(gameView);
        frame.pack();

        GameObject gameObject = new GameObject(gameController, gameView);

        BlackScreenScene arena = new BlackScreenScene(new Bounds(1, 1));
        SwingPawn pawn = new SwingPawn(new Location(.1f, .1f), new Bounds(.2f, .2f));
        arena.getActors().add(pawn);
        GameSession session = new GameSession(arena, pawn);
        session.addTickListener(pawn);

        frame.setVisible(true);
        gameObject.startSession(session);
    }
}
