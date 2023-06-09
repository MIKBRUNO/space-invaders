import game.guardian.GuardianControllerEvent;
import game.engine.smart_register.SmartSubscribing;
import game.engine.types.Vector;
import game.engine.controller.ControllerObserver;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingController extends SmartSubscribing<ControllerObserver<GuardianControllerEvent>> {
    @Override
    protected void innerRegister(ControllerObserver<GuardianControllerEvent> vectorControllerObserver) {
        Observer = vectorControllerObserver;
    }

    @Override
    public void onDeathCallback(ControllerObserver<GuardianControllerEvent> deadSubscriber) {
        Observer = null;
    }

    public SwingController() {
        Observer = null;
    }

    public final KeyListener keyListener = new KeyAdapter() {
        private final boolean[] PressedDirections = {false, false};
        @Override
        public void keyPressed(KeyEvent e) {
            if (Observer == null)
                return;
            boolean fire = false;
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> PressedDirections[0] = true;
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> PressedDirections[1] = true;
                case KeyEvent.VK_SPACE -> {
                    if (isPossibleToFireAgain) {
                        fire = true;
                        isPossibleToFireAgain = false;
                    }
                }
            }
            float res = 0;
            if (PressedDirections[0])
                res -= 1;
            if (PressedDirections[1]) {
                res += 1;
            }
            Observer.controllerUpdate(new GuardianControllerEvent(new Vector(res, 0), fire));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (Observer == null)
                return;
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> PressedDirections[0] = false;
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> PressedDirections[1] = false;
                case KeyEvent.VK_SPACE -> isPossibleToFireAgain = true;
            }
            float res = 0;
            if (PressedDirections[0])
                res -= 1;
            if (PressedDirections[1]) {
                res += 1;
            }
            Observer.controllerUpdate(new GuardianControllerEvent(new Vector(res, 0), false));
        }
    };

    private boolean isPossibleToFireAgain = true;

    private ControllerObserver<GuardianControllerEvent> Observer;
}
