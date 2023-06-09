package game.engine.overlapping;

import game.engine.types.Bounds;
import game.engine.types.Location;

public interface OverlappingObject {
    void eventOnOverlap(OverlappingObject other, OverlapFactor tag);

    Location getOverlapLocation();

    Bounds getOverlapBounds();
}
