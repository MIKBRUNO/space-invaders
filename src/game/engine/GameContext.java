package game.engine;

import game.engine.overlapping.OverlapManager;
import game.engine.smart_register.SmartSubscribing;
import game.engine.view.Actor;
import game.engine.view.GameView;
import game.engine.view.Scene;

public class GameContext {
    public GameContext(GameView<? extends Actor> view, SmartSubscribing<?> controller) {
        SessionView = view;
        SessionController = controller;
    }

    public OverlapManager getOverlapManager() {
        return SessionOverlapManager;
    }

    public GameLoop getGameLoop() {
        return SessionGameLoop;
    }

    public SmartSubscribing<?> getController() {
        return SessionController;
    }

    public GameView<? extends Actor> getView() {
        return SessionView;
    }

    public Scene<? extends Actor> getScene() {
        return SessionScene;
    }

    public void bindScene(Scene<? extends Actor> scene) {
        SessionView.setScene(scene);
    }

    private Scene<? extends Actor> SessionScene;

    private final GameView<? extends Actor> SessionView;

    private final SmartSubscribing<?> SessionController;

    private final OverlapManager SessionOverlapManager = new OverlapManager();

    private final GameLoop SessionGameLoop = new GameLoop();

}
