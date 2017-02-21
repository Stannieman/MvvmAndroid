package stannieman.mvvm.navigation;

import android.app.Activity;
import android.os.Bundle;

import stannieman.mvvm.ViewBase;

/**
 * Defines a service to navigate between activities.
 */
public interface INavigationService {
    /**
     * Returns the current activity.
     * @return the current activity
     */
    Activity getCurrentActivity();

    /**
     * Sets the current activity.
     * This method should not be used and is only here to support ViewBase.
     * @param currentActivity the current activity
     */
    void setCurrentActivity(Activity currentActivity);

    /**
     * Navigates to the view of the given type.
     * @param viewType the view type to navigate to
     * @param <ViewType> the type of the view
     */
    <ViewType extends ViewBase> void navigateTo(Class<ViewType> viewType);

    /**
     * Navigates to the view of the given type.
     * @param viewType the view type to navigate to
     * @param parameters Bundle instance with parameters that need to be passed to the destination view and view model.
     * @param <ViewType> the type of the view
     */
    <ViewType extends ViewBase> void navigateTo(Class<ViewType> viewType, Bundle parameters);

    /**
     * Navigates to the previous activity and finishes the current one.
     */
    void navigateBack();
}
