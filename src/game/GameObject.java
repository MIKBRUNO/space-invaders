package game;

import game.controller.GameController;
import game.view.GameView;

public class GameObject {
    public GameObject(GameController controller, GameView view) {
        Controller = controller;
        View = view;
    }
    public void startSession(GameSession session) {
        session.bindController(Controller);
        session.bindView(View);
        session.start();
    }
    private final GameController Controller;
    private final GameView View;
}
