package game.engine.view;

import game.engine.GameSession;
import game.engine.actors.Actor;
import game.engine.smart_register.SmartSubscribing;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ViewManager extends SmartSubscribing<Actor> {
    public abstract void update(GameSession.GameContext context);

    protected final Collection<ActorView> getActorViews() {
        return ActorMap.values();
    }

    @Override
    protected final void innerRegister(Actor actor) {
        ActorView view = ActorViewFactory.createViewForActor(actor);
        if (view != null) {
            view.setActor(actor);
        }
        ActorMap.put(actor, view);
    }

    @Override
    public final void onDeathCallback(Actor deadSubscriber) {
        ActorMap.remove(deadSubscriber);
    }

    private final Map<Actor, ActorView> ActorMap = new ConcurrentHashMap<>();

    protected final static class ActorViewFactory {
        public static void setClassMap(InputStream in) {
            ClassActorMap.clear();
            try {
                Scanner scanner = new Scanner(in);
                while (scanner.hasNext()) {
                    String[] pair = scanner.nextLine().split("\\s*->\\s*");
                    if (pair.length < 2)
                        throw new RuntimeException("bad configuration file");
                    ClassActorMap.put((Class<Actor>) Class.forName(pair[0]),
                            (Class<ActorView>) Class.forName(pair[1]));
                }
            } catch (ClassCastException | ClassNotFoundException e) {
                throw new RuntimeException("bad configuration file");
            }
        }

        public static ActorView createViewForActor(Actor actor) {
            Class<ActorView> actorViewClass = ClassActorMap.get(actor.getClass());
            if (actorViewClass == null) {
                return null;
            }
            try {
                return actorViewClass.getConstructor().newInstance();
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException("bad ActorView implementation: " + actorViewClass.getName());
            }
        }

        private static final Map<Class<Actor>, Class<ActorView>> ClassActorMap = Collections.synchronizedMap(
                new HashMap<>()
        );
    }
}
