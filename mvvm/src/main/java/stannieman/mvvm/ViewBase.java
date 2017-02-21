package stannieman.mvvm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import stannieman.mvvm.messaging.IHandle;
import stannieman.mvvm.messaging.IMessenger;
import stannieman.mvvm.navigation.INavigationService;

/**
 * Base class for views.
 * @param <T> type of the view model to bind to
 */
public abstract class ViewBase<T extends ViewModelBase> extends AppCompatActivity implements IHandle {

    //region navigationService

    private INavigationService navigationService;

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

    //region viewModel

    private T viewModel;

    protected T getViewModel() {
        return viewModel;
    }

    @Inject
    public void setViewModel(T viewModel) {
        this.viewModel = viewModel;
    }

    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.onCreate(savedInstanceState, getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationService.setCurrentActivity(this);
        viewModel.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        viewModel.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        viewModel.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        viewModel.onDestroy();
    }

    /**
     * Handles published messages.
     * Note that views are not automatically subscribe,
     * if you want to receive messages you should subscribe in your own view implementation.
     * @param message published message
     */
    public void Handle(Object message) {}
}
