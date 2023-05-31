package game.engine.smart_register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LivingSubscriber<TSubscriber> {
    public LivingSubscriber(TSubscriber subscriber) {
        HeldSub = subscriber;
    }

    public synchronized TSubscriber getSubscriber() {
        return HeldSub;
    }

    public void addDieListener(SmartSubscribing<? super TSubscriber> subscribing) {
        Listeners.add(subscribing);
    }

    public synchronized void destroy() {
        synchronized (Listeners) {
            for (SmartSubscribing<? super TSubscriber> listener : Listeners) {
                listener.onDeathCallback(HeldSub);
            }
        }
    }

    private final List<SmartSubscribing<? super TSubscriber>> Listeners = Collections.synchronizedList( new ArrayList<>() );

    private final TSubscriber HeldSub;
}
