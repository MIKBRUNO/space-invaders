package game;

public record Vector(float x, float y) {
    Vector sum(float scalar, Vector other) {
        return new Vector(x + scalar * other.x, y + scalar * other.y);
    }
    Vector mul(float scalar) {
        return new Vector(x * scalar, y * scalar);
    }
}
