package stannieman.mvvm.messaging;

import java.util.HashSet;
import java.util.Set;
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
    private final Set<IHandle> subscribers = new HashSet<>();

    @Inject
    public Messenger(final IMainThreadHelper mainThreadHelper) {
        this.mainThreadHelper = mainThreadHelper;
    }

    /**
     * Subscribes a handler to the messenger.
     * The subscribed handler will receive all published messages.
     * @param subscriber handler to subscribe
     */
    @Override
    public void subScribe(final IHandle subscriber) {
        subscribersLock.lock();
        subscribers.add(subscriber);
        subscribersLock.unlock();
    }

    /**
     * Unsuibscribes a handler from the messenger.
     * The handler will no longer receive any messages.
     * @param subscriber handler to unsubscribe
     */
    @Override
    public void unSubscribe(final IHandle subscriber) {
        subscribersLock.lock();
        subscribers.remove(subscriber);
        subscribersLock.unlock();
    }

    /**
     * Publishes a message to all subscribed handlers.
     * @param message message to publish
     */
    @Override
    public void publish(final Object message) {
        subscribersLock.lock();
        final Set<IHandle> copiedSubscribers = new HashSet<>(subscribers);
        subscribersLock.unlock();

        mainThreadHelper.postToMainThreadIfNeeded(new Runnable() {
            @Override
            public void run() {
                for (IHandle subscriber : copiedSubscribers) {
                    subscriber.Handle(message);
                }
            }
        });
    }
}
