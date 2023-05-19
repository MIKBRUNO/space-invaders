package game;

public record Location(float x, float y) {
    Location plusV(float scalar, Vector v) {
        return new Location(x + scalar * v.x(), y + scalar * v.y());
    }
}
