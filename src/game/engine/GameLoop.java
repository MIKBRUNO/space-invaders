package game.engine;

import game.engine.smart_register.SmartSubscribing;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class GameLoop extends SmartSubscribing<EventTickListener> {
    public GameLoop(GameContext context) {
        Context = context;
    }

    public synchronized void start() {
        state = State.RUNNING;
        gameThread.start();
        notify();
    }

    public synchronized void stop() {
        state = State.STOPPED;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        notify();
    }

    public synchronized void pause() {
        state = State.PAUSED;
        notify();
    }

    public synchronized void proceed() {
        state = State.RUNNING;
        notify();
    }

    private State state = State.STOPPED;

    private synchronized boolean isStopped() {
        return state == State.STOPPED;
    }

    private enum State {
        RUNNING,
        PAUSED,
        STOPPED
    }

    private final Thread gameThread = new Thread(this::threadWork, "GameLoop");

    private void threadWork() {
        long deltaNanos;
        long nanos1 = System.nanoTime();
        long nanos2;
        while (!isStopped() && !gameThread.isInterrupted()) {
            synchronized (this) {
                if (state == State.PAUSED) {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
            }
            nanos2 = System.nanoTime();
            deltaNanos = nanos2 - nanos1;
            nanos1 = System.nanoTime();
            for (EventTickListener listener : Listeners) {
                listener.eventTick((deltaNanos) / 1.e9f);
            }
            Context.getOverlapManager().update();
            Context.getViewManager().update(Context);
        }
    }

    private final GameContext Context;

    private final List<EventTickListener> Listeners = new CopyOnWriteArrayList<>();

    @Override
    protected void innerRegister(EventTickListener eventTickListener) {
        Listeners.add(eventTickListener);
    }

    @Override
    public void onDeathCallback(EventTickListener deadSubscriber) {
        Listeners.remove(deadSubscriber);
    }
}
