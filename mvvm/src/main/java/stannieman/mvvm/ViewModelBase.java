package stannieman.mvvm;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import stannieman.mvvm.messaging.IHandle;
import stannieman.mvvm.messaging.IMessenger;
import stannieman.mvvm.navigation.INavigationService;

/**
 * Base class for view models.
 */
public abstract class ViewModelBase extends BaseObservable implements IHandle {
    private boolean firstCreateDone;
    private boolean firstStartDone;
    private boolean firstResumeDone;

    //region navigationService

    private INavigationService navigationService;

    protected INavigationService getNavigationService() {
        return navigationService;
    }

    @Inject
    public void setNavigationService(INavigationService navigationService) {
        this.navigationService = navigationService;
    }

    //endregion

    //region messenger

    private IMessenger messenger;

    protected IMessenger getMessenger() {
        return messenger;
    }

    @Inject
    public void setMessenger(IMessenger messenger) {
        this.messenger = messenger;
    }

    //endregion

    public void onCreate(@Nullable Bundle savedInstanceState, Intent intent) {
        if (!firstCreateDone) {
            onFirstCreate();
            firstCreateDone = true;
        }
    }

    /**
     * Runs the first time onCreate is called.
     * This method is called by onCreate, so the exact moment it
     * runs depends on when you call super.onCreate.
     */
    protected void onFirstCreate() {}

    public void onStart() {
        if (!firstStartDone) {
            onFirstStart();
            firstStartDone = true;
        }
    }

    /**
     * Runs the first time onStart is called.
     * This method is called by onStart, so the exact moment it
     * runs depends on when you call super.onStart.
     */
    protected void onFirstStart() {}

    public void onResume() {
        if (!firstResumeDone) {
            onFirstResume();
            firstResumeDone = true;
        }
    }

    /**
     * Runs the first time onResume is called.
     * This method is called by onResume, so the exact moment it
     * runs depends on when you call super.onResume.
     */
    protected void onFirstResume() {}

    public void onRestart() {}

    public void onPause() {}

    public void onStop() {}

    public void onDestroy() {}

    /**
     * Handles published messages.
     * Note that views are not automatically subscribe,
     * if you want to receive messages you should subscribe in your own view implementation.
     * @param message published message
     */
    public void Handle(Object message) {}
}
