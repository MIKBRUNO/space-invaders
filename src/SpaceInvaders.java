import game.Guardian;
import game.GuardianControllerEvent;
import game.engine.GameSession;
import game.engine.controller.ControllerObserver;
import game.engine.overlapping.OverlapFactor;
import game.engine.smart_register.SmartSubscribing;
import game.engine.types.Bounds;
import game.engine.types.Location;
import game.engine.view.Scene;
import game.engine.view.ViewManager;

public class SpaceInvaders {
    public SpaceInvaders(
            SmartSubscribing<ControllerObserver<GuardianControllerEvent>> controller,
            ViewManager viewManager,
            Bounds sceneBounds
    ) {
        Scene scene = () -> sceneBounds;
        gameSession = new GameSession(scene, viewManager);
        Context = gameSession.Context;
        Guardian pawn = new Guardian(
                gameSession.Context,
                new Location(0.1f, sceneBounds.height() - 0.2f),
                new Bounds(0.1f, 0.1f)
        );
        controller.register(pawn.getLivingInstance());
        gameSession.Context.getSpawner().registerOverlappingActor(pawn.getLivingInstance(), OverlapFactor.GUARDIAN);
    }

    public void run() {
        Context.getLoop().start();
    }

    public final GameSession.GameContext Context;
    private final GameSession gameSession;
}
