package game;

import game.engine.GameSession;
import game.engine.actors.ArenaActor;
import game.engine.types.Bounds;
import game.engine.types.Location;

public abstract class ContextualizedArenaActor extends ArenaActor {
    public ContextualizedArenaActor(GameSession.GameContext context, Location location, Bounds bounds) {
        super(context.getScene(), location, bounds);
        Context = context;
    }

    protected final GameSession.GameContext Context;
}
