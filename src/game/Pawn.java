package game;

import game.controller.ControllerObserver;
import game.view.Actor;

public class Pawn implements ControllerObserver<Vector>, Actor, EventTickListener {
    @Override
    public void eventTick(float deltaSeconds) {
        setLocation(PawnLocation.plusV(deltaSeconds, Speed));
    }

    @Override
    public void update(Vector e) {
        final float speed = 1.e-12f;
        Speed = e.mul(speed);
    }

    @Override
    public Location getLocation() {
        return PawnLocation;
    }

    @Override
    public Bounds getBounds() {
        return PawnBounds;
    }

    @Override
    public void setLocation(Location location) {
        PawnLocation = location;
    }

    @Override
    public void setBounds(Bounds bounds) {
        PawnBounds = bounds;
    }

    private Location PawnLocation = new Location(0, 0);
    private Bounds PawnBounds = new Bounds(0, 0);
    private Vector Speed = new Vector(0, 0);
}
