package game.engine.controller;

public interface ControllerObserver<ControlledParameter> {
    void controllerUpdate(ControlledParameter param);
}
