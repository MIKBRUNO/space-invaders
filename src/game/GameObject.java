package game;

import game.controller.GameController;

public class GameObject {
    public GameObject(GameController controller) {
        Controller = controller;
    }
    public void startSession(GameSession session) {

    }
    private final GameController Controller;
}
