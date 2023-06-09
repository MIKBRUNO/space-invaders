package game.enemies;

import game.SIContext;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.types.Vector;

public class Crab extends Enemy {
    public Crab(SIContext context, Location location, Bounds bounds, Vector leftStep, Vector downStep) {
        super(context, location, bounds, leftStep, downStep);
    }

    @Override
    public int getScorePoints() {
        return 20;
    }
}
