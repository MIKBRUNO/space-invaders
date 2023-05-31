package game.engine;

import game.engine.actors.Actor;
import game.engine.actors.MovingActor;
import game.engine.actors.OverlappingActor;
import game.engine.overlapping.OverlapFactor;
import game.engine.overlapping.OverlapManager;
import game.engine.smart_register.LivingSubscriber;
import game.engine.smart_register.SmartSubscribing;
import game.engine.view.Scene;
import game.engine.view.ViewManager;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameSession {
    public GameSession(Scene scene, ViewManager viewManager) {
        COverlapManager = new OverlapManager();
        CViewManager = viewManager;
        CScene = scene;
        Spawner = new ActorRegister();
        Loop = new GameLoop();
    }

    public final GameContext Context = new GameContext();

    public final class GameContext {
        public synchronized OverlapManager getOverlapManager() {
            return COverlapManager;
        }

        public synchronized SmartSubscribing<EventTickListener> getEventTickController() {
            return EventTickController;
        }

        public synchronized ViewManager getViewManager() {
            return CViewManager;
        }

        public synchronized Scene getScene() {
            return CScene;
        }

        public synchronized ActorRegister getSpawner() {
            return Spawner;
        }

        public synchronized GameLoop getLoop() {
            return Loop;
        }
    }

    public final class GameLoop {
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
                    listener.eventTick((deltaNanos) / 1.e6f);
                }
                Context.getOverlapManager().update();
                Context.getViewManager().update(Context);
            }
        }
    }

    public final class ActorRegister {
        public void registerActor(LivingSubscriber<? extends Actor> actor) {
            Context.getViewManager().register(actor);
        }

        public void registerMovingActor(LivingSubscriber<? extends MovingActor> actor) {
            Context.getViewManager().register(actor);
            Context.getEventTickController().register(actor);
        }

        public void registerOverlappingActor(LivingSubscriber<? extends OverlappingActor> actor, OverlapFactor tag) {
            Context.getViewManager().register(actor);
            Context.getEventTickController().register(actor);
            Context.getOverlapManager().registerOverlappingObject(actor, tag);
        }
    }

    private final OverlapManager COverlapManager;
    private final ViewManager CViewManager;
    private final Scene CScene;
    private final ActorRegister Spawner;
    private final GameLoop Loop;
    private final List<EventTickListener> Listeners = new CopyOnWriteArrayList<>();
    private final SmartSubscribing<EventTickListener> EventTickController = new SmartSubscribing<EventTickListener>() {
        @Override
        protected void innerRegister(EventTickListener eventTickListener) {
            Listeners.add(eventTickListener);
        }

        @Override
        public void onDeathCallback(EventTickListener deadSubscriber) {
            Listeners.remove(deadSubscriber);
        }
    };
}
