package game;

import game.controller.ControllerEvent;
import game.controller.ControllerObserver;

public class Pawn implements ControllerObserver, Actor {
    @Override
    public void update(ControllerEvent e) {

    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Bounds getBounds() {
        return null;
    }

    @Override
    public void setLocation(Location location) {

    }

    @Override
    public void setBounds(Bounds bounds) {

    }
}
