package stannieman.mvvm.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import stannieman.mvvm.ViewBase;
import stannieman.mvvm.helpers.IMainThreadHelper;

/**
 * Aervice to navigate between activities.
 */
public final class NavigationService implements INavigationService {
    private final Context context;
    private final IMainThreadHelper mainThreadHelper;
    private final Runnable navigateBackRunnable;

    //region currentActivity

    private Activity currentActivity;

    /**
     * Returns the current activity.
     * @return the current activity
     */
    public Activity getCurrentActivity() {
        return this.currentActivity;
    }

    /**
     * Sets the current activity.
     * This method should not be used and is only here to support ViewBase.
     * @param currentActivity the current activity
     */
    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    //endregion

    @Inject
    public NavigationService(Context context, IMainThreadHelper mainThreadHelper) {
        this.context = context;
        this.mainThreadHelper = mainThreadHelper;
        navigateBackRunnable = new Runnable() {
            @Override
            public void run() {
                currentActivity.finish();
            }
        };
    }

    /**
     * Navigates to the view of the given type.
     * @param viewType the view type to navigate to
     * @param <ViewType> the type of the view
     */
    public <ViewType extends ViewBase> void navigateTo(Class<ViewType> viewType) {
        final Intent intent = new Intent(this.context, viewType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mainThreadHelper.postToMainThreadIfNeeded(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        });
    }

    /**
     * Navigates to the view of the given type.
     * @param viewType the view type to navigate to
     * @param parameters Bundle instance with parameters that need to be passed to the destination view and view model.
     * @param <ViewType> the type of the view
     */
    public <ViewType extends ViewBase> void navigateTo(Class<ViewType> viewType, Bundle parameters) {
        final Intent intent = new Intent(this.context, viewType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(parameters);

        mainThreadHelper.postToMainThreadIfNeeded(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        });
    }

    /**
     * Navigates to the previous activity and finishes the current one.
     */
    public void navigateBack() {
        mainThreadHelper.postToMainThreadIfNeeded(navigateBackRunnable);
    }
}
