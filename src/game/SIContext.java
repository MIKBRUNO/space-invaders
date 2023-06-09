package game;

import game.engine.GameContext;
import game.engine.types.Bounds;

public interface SIContext extends GameContext {
    ScoreCounter getScoreCounter();
    void setGameState(GameState state);
    Bounds getBulletBounds();
    float getGuardianSpeed();
}
