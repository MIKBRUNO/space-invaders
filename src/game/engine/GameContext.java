package game.engine;

import game.engine.overlapping.OverlapManager;
import game.engine.smart_register.SmartSubscribing;
import game.engine.view.Scene;
import game.engine.view.ViewManager;

// every method should be synchronized (in case there are threading)
public interface GameContext {
    OverlapManager getOverlapManager();
    SmartSubscribing<EventTickListener> getEventTickController();
    ViewManager getViewManager();
    Scene getScene();
    ActorRegister getSpawner();
    GameLoop getLoop();
}
