package game.engine;

import game.engine.smart_register.SmartSubscribing;
import game.engine.view.GameView;

import java.util.ArrayList;

public class GameLoop extends SmartSubscribing<EventTickListener> {
    public GameLoop(GameContext context) {
        Context = context;
    }

    public void start() {
        // start game thread
        // link game thread and view
        // https://www.oracle.com/java/technologies/painting.html
        long deltaNanos;
        long nanos1 = System.nanoTime();
        long nanos2;
        while (true) {
            nanos2 = System.nanoTime();
            deltaNanos = nanos2 - nanos1;
            nanos1 = System.nanoTime();
            for (EventTickListener listener : TickListeners) {
                listener.eventTick((deltaNanos) / 1.e6f);
            }
            Context.getOverlapManager().update();
            Context.getView().update();
        }
    }

    @Override
    protected void innerRegister(EventTickListener eventTickListener) {
        TickListeners.add(eventTickListener);
    }

    @Override
    public void onDeathCallback(EventTickListener deadSubscriber) {
        TickListeners.remove(deadSubscriber);
    }

    private final ArrayList<EventTickListener> TickListeners = new ArrayList<>();

    private final GameContext Context;
}
