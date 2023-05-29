package game.engine.smart_register;

public abstract class SmartSubscribing<TSubscriber> {
    protected abstract void innerRegister(TSubscriber subscriber);

    public void register(LivingSubscriber<? extends TSubscriber> subscriber) {
        subscriber.addDieListener(this);
        innerRegister(subscriber.getSubscriber());
    }

    public abstract void onDeathCallback(TSubscriber deadSubscriber);
}
