package stannieman.mvvm.messaging;

/**
 * Defines a messenger where handlers can be subscribed to
 * and that publishes messages to subscribed handlers.
 */
public interface IMessenger {
    /**
     * Subscribes a handler to the messenger.
     * The subscribed handler will receive all published messages
     * for which it has IHandle<T> implemented where T is the type of the message.
     * @param subscriber handler to subscribe
     */
    void subScribe(IHandle<?> subscriber);

    /**
     * Unsubscribes a handler from the messenger.
     * The handler will no longer receive any messages.
     * @param subscriber handler to unsubscribe
     */
    void unSubscribe(IHandle<?> subscriber);

    /**
     * Publishes a message to all subscribed handlers that can receive this type of message.
     * @param message message to publish
     * @param <T> Type of the message to send.
     */
    <T> void publish(T message);
}
