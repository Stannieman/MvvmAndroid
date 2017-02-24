package stannieman.mvvm.messaging;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;

import stannieman.mvvm.helpers.IMainThreadHelper;

/**
 * A messenger where handlers can be subscribed to
 * and that publishes messages to subscribed handlers.
 */
public final class Messenger implements IMessenger {
    private final IMainThreadHelper mainThreadHelper;

    private final Lock subscribersLock = new ReentrantLock();
    private final Map<Object, Object> subscribers;

    /**
     * Constructor that takes an IMainTreadHelper.
     * @param mainThreadHelper helper to invoke message handlers on the main thread.
     */
    @Inject
    public Messenger(final IMainThreadHelper mainThreadHelper) {
        this.mainThreadHelper = mainThreadHelper;
        subscribers = new WeakHashMap<>();
    }

    /**
     * Subscribes a handler to the messenger.
     * The subscribed handler will receive all published messages
     * for which it has IHandle<T> implemented where T is the type of the message.
     * @param subscriber handler to subscribe
     */
    @Override
    public void subScribe(final Object subscriber) {
        subscribersLock.lock();
        subscribers.put(subscriber, null);
        subscribersLock.unlock();
    }

    /**
     * Unsubscribes a handler from the messenger.
     * The handler will no longer receive any messages.
     * @param subscriber handler to unsubscribe
     */
    @Override
    public void unSubscribe(final Object subscriber) {
        subscribersLock.lock();
        subscribers.remove(subscriber);
        subscribersLock.unlock();
    }

    /**
     * Publishes a message to all subscribed handlers.
     * @param message message to publish
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void publish(final T message) {
        subscribersLock.lock();
        final Set<Object> copiedSubscribers = subscribers.keySet();
        subscribersLock.unlock();

        mainThreadHelper.postToMainThreadIfNeeded(new Runnable() {
            @Override
            public void run() {
                for (Object subscriber : copiedSubscribers) {
                    Type[] genericInterfaces = subscriber.getClass().getGenericInterfaces();
                    for (Type genericInterface : genericInterfaces) {
                        if (genericInterface instanceof ParameterizedType) {
                            Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                            for (Type type : genericTypes) {
                                if (type == message.getClass()) {
                                    ((IHandle<T>)subscriber).Handle(message);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
