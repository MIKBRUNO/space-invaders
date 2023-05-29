import game.engine.Arena;
import game.engine.overlapping.OverlapFactor;
import game.engine.types.Bounds;
import game.engine.GameContext;
import game.engine.types.Location;
import swing_view.SwingGameView;
import swing_view.SwingGuardian;

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

        SwingController gameController = new SwingController();
        frame.addKeyListener(gameController.keyListener);

        SwingGameView gameView = new SwingGameView();
        frame.add(gameView);
        frame.pack();

        Arena arena = new Arena(new Bounds(1, (float)gameView.getHeight()/gameView.getWidth()));
        SwingGuardian pawn = new SwingGuardian(arena, new Location(0.1f, 0.1f), new Bounds(0.1f, 0.1f));
        arena.register(pawn.getLivingInstance());

        GameContext session = new GameContext(arena, pawn.getLivingInstance());

        session.bindScene(arena);


        session.register(pawn.getLivingInstance());
        session.SessionOverlapManager.registerOverlappingObject(pawn.getLivingInstance(), OverlapFactor.GUARDIAN);

        frame.setVisible(true);
        session.bindView(gameView);
        session.bindController(gameController);
//        gameController.register(pawn.getInstance());
//        gameObject.startSession(session);
        session.start();
    }
}
