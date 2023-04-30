import game.controller.ControllerEvent;
import game.controller.ControllerObserver;
import game.controller.GameController;

import javax.swing.*;

public class SpaceInvaders {
    public static void main(String[] args) {
        JFrame frame = new JFrame("title");
        frame.setSize(720, 480);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        SwingController gameController = new SwingController((e) -> {});
        frame.addKeyListener(gameController.keyListener);

        frame.setVisible(true);
    }
}
