package stannieman.mvvm.messaging;

/**
 * Defines a class that can receive messages.
 * @param <T> type of the message to receive
 */

public interface IHandle<T> {
    /**
     * Handles a published message.
     * @param message published message
     */
    void Handle(T message);
}
