package game.engine.smart_register;

import java.util.ArrayList;

public class LivingSubscriber<TSubscriber> {
    public LivingSubscriber(TSubscriber subscriber) {
        HeldSub = subscriber;
    }

    public TSubscriber getSubscriber() {
        return HeldSub;
    }

    public void addDieListener(SmartSubscribing<? super TSubscriber> subscribing) {
        Listeners.add(subscribing);
    }

    public void destroy() {
        for (SmartSubscribing<? super TSubscriber> listener : Listeners) {
            listener.onDeathCallback(HeldSub);
        }
    }

    private final ArrayList<SmartSubscribing<? super TSubscriber>> Listeners = new ArrayList<>();

    private final TSubscriber HeldSub;
}
