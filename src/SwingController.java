import game.Vector;
import game.controller.ControllerEvent;
import game.controller.ControllerObserver;
import game.controller.GameController;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingController implements GameController<Vector> {
    public SwingController(ControllerObserver<Vector> observer) {
        setObserver(observer);
    }

    public SwingController() {
        setObserver(null);
    }

    @Override
    public void setObserver(ControllerObserver<Vector> observer) {
        Observer = observer;
    }

    public final KeyListener keyListener = new KeyAdapter() {
        private final boolean[] PressedDirections = {false, false};
        @Override
        public void keyPressed(KeyEvent e) {
            if (Observer == null)
                return;
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> PressedDirections[0] = true;
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> PressedDirections[1] = true;
            }
            float res = 0;
            if (PressedDirections[0])
                res -= 1;
            if (PressedDirections[1]) {
                res += 1;
            }
            Observer.update(new Vector(res, 0));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (Observer == null)
                return;
            ControllerEvent cevent = ControllerEvent.NONE;
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> PressedDirections[0] = false;
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> PressedDirections[1] = false;
            }
            float res = 0;
            if (PressedDirections[0])
                res -= 1;
            if (PressedDirections[1]) {
                res += 1;
            }
            Observer.update(new Vector(res, 0));
        }
    };

    private ControllerObserver<Vector> Observer;
}
