import game.controller.ControllerEvent;
import game.controller.ControllerObserver;
import game.controller.GameController;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingController implements GameController {
    public SwingController(ControllerObserver observer) {
        setObserver(observer);
    }

    @Override
    public void setObserver(ControllerObserver observer) {
        Observer = observer;
    }

    public final KeyListener keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            ControllerEvent cevent = ControllerEvent.NONE;
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_A -> cevent = ControllerEvent.LEFT_ARROW_PRESSED;
                case KeyEvent.VK_D -> cevent = ControllerEvent.RIGHT_ARROW_PRESSED;
            }
            Observer.update(cevent);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            ControllerEvent cevent = ControllerEvent.NONE;
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_A -> cevent = ControllerEvent.LEFT_ARROW_RELEASED;
                case KeyEvent.VK_D -> cevent = ControllerEvent.RIGHT_ARROW_RELEASED;
            }
            Observer.update(cevent);
        }
    };

    private ControllerObserver Observer;
}
