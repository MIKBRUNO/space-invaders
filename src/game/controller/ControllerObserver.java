package game.controller;

public interface ControllerObserver<ControlledParameterClass> {
    void update(ControlledParameterClass param);
}
