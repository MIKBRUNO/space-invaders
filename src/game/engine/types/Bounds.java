package game.engine.types;

public record Bounds(float width, float height) {
    public Bounds mulS(float wscalar, float hscalar) {
        return new Bounds(width * wscalar, height * hscalar);
    }
}
