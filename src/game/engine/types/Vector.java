package game.engine.types;

public record Vector(float x, float y) {
    public Vector sum(float scalar, Vector other) {
        return new Vector(x + scalar * other.x, y + scalar * other.y);
    }
    public Vector mul(float scalar) {
        return new Vector(x * scalar, y * scalar);
    }
}
