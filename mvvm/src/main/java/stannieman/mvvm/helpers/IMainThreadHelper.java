package stannieman.mvvm.helpers;

/**
 * Defines a class to run a runnable on the main thread.
 */
public interface IMainThreadHelper {

    /**
     * Returns whether the calling thread is the main thread.
     * @return whether the calling thread is the main thread
     */
    boolean isMainThread();

    /**
     * Posts a runnable to the main thread.
     * @param runnable runnable to post to the main thread
     */
    void postToMainThread(Runnable runnable);

    /**
     * Runs a runnable synchronous if the calling thread is the main thread,
     * posts the runnable to the main thread otherwise.
     * @param runnable runnable to run on the main thread
     */
    void postToMainThreadIfNeeded(Runnable runnable);
}
