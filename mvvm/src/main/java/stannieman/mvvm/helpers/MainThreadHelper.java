package stannieman.mvvm.helpers;

import android.os.Handler;
import android.os.Looper;

import javax.inject.Inject;

/**
 * Class to run a runnable on the main thread.
 */
public final class MainThreadHelper implements IMainThreadHelper {
    private final Handler mainThreadHandler;

    @Inject
    public MainThreadHelper() {
        mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Returns whether the calling thread is the main thread.
     * @return whether the calling thread is the main thread
     */
    @Override
    public boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * Posts a runnable to the main thread.
     * @param runnable runnable to post to the main thread
     */
    @Override
    public void postToMainThread(final Runnable runnable) {
        mainThreadHandler.post(runnable);
    }

    /**
     * Runs a runnable synchronous if the calling thread is the main thread,
     * posts the runnable to the main thread otherwise.
     * @param runnable runnable to run on the main thread
     */
    @Override
    public void postToMainThreadIfNeeded(final Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        }
        else {
            postToMainThread(runnable);
        }
    }
}
