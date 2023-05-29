package game.engine.types;

public record Location(float x, float y) {
    public Location plusV(float scalar, Vector v) {
        return new Location(x + scalar * v.x(), y + scalar * v.y());
    }

    public Location plus(float xx, float yy) {
        return new Location(x + xx, y + yy);
    }
}
