package in.co.rakshit.samplefloat.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import in.co.rakshit.samplefloat.data.DataManager;
import in.co.rakshit.samplefloat.data.local.PreferencesHelper;
import in.co.rakshit.samplefloat.data.remote.LookUpService;
import in.co.rakshit.samplefloat.injection.ApplicationContext;
import in.co.rakshit.samplefloat.injection.module.ApplicationModule;
import in.co.rakshit.samplefloat.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

//    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    LookUpService ribotsService();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
