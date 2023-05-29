package game.engine.controller;

public interface ControllerObserver<ControlledParameter> {
    void update(ControlledParameter param);
}
