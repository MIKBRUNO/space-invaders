package game.controller;

public interface GameController<ControlledParameterClass> {

    void setObserver(ControllerObserver<ControlledParameterClass> observer);

}
