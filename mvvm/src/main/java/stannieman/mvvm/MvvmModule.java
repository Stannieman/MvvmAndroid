package stannieman.mvvm;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import stannieman.mvvm.helpers.IMainThreadHelper;
import stannieman.mvvm.helpers.MainThreadHelper;
import stannieman.mvvm.messaging.IMessenger;
import stannieman.mvvm.messaging.Messenger;
import stannieman.mvvm.navigation.INavigationService;
import stannieman.mvvm.navigation.NavigationService;

@Module
public abstract class MvvmModule {
    @Singleton
    @Binds
    public abstract IMainThreadHelper bindIMainThreadHelper(MainThreadHelper mainThreadHelper);
    @Singleton
    @Binds
    public abstract IMessenger bindIMessenger(Messenger messenger);
    @Singleton
    @Binds
    public abstract INavigationService bindINavigationService(NavigationService navigationService);
}
