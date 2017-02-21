package stannieman.mvvm.messaging;

/**
 * Defines a messenger where handlers can be subscribed to
 * and that publishes messages to subscribed handlers.
 */
public interface IMessenger {
    /**
     * Subscribes a handler to the messenger.
     * The subscribed handler will receive all published messages.
     * @param subscriber handler to subscribe
     */
    void subScribe(IHandle subscriber);

    /**
     * Unsuibscribes a handler from the messenger.
     * The handler will no longer receive any messages.
     * @param subscriber handler to unsubscribe
     */
    void unSubscribe(IHandle subscriber);

    /**
     * Publishes a message to all subscribed handlers.
     * @param message message to publish
     */
    void publish(Object message);
}
