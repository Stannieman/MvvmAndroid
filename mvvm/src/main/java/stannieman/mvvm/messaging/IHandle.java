package stannieman.mvvm.messaging;

/**
 * Defines a class that can receive messages.
 */
public interface IHandle {
    /**
     * Handles a published message.
     * @param message published message
     */
    void Handle(Object message);
}
