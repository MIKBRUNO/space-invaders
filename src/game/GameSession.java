package game;

import game.controller.ControllerObserver;
import game.controller.GameController;
import game.view.GameView;

import java.util.ArrayList;

public class GameSession {
    public GameSession(ArrayListScene scene, ControllerObserver controllerObserver) {
        SessionScene = scene;
        SessionPawn = controllerObserver;
    }
    public void bindController(GameController controller) {
        controller.setObserver(SessionPawn);
    }
    public void bindView(GameView view) {
        view.setScene(SessionScene);
        View = view;
    }
    public void addTickListener(EventTickListener listener) {
        TickListeners.add(listener);
    }
    public void start() {
        // start game thread
        // link game thread and view
        // https://www.oracle.com/java/technologies/painting.html
        long nanos = System.nanoTime();
        while (true) {
            nanos = System.nanoTime() - nanos;
            for (EventTickListener listener : TickListeners) {
                listener.eventTick(nanos / 1.e9f);
            }
            View.update();
        }
    }
    private ArrayListScene SessionScene;
    private ControllerObserver SessionPawn;
    private GameView View;
    private ArrayList<EventTickListener> TickListeners = new ArrayList<>(64);
}
