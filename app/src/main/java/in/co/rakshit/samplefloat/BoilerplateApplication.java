package in.co.rakshit.samplefloat;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import in.co.rakshit.samplefloat.injection.component.ApplicationComponent;
import in.co.rakshit.samplefloat.injection.component.DaggerApplicationComponent;
import in.co.rakshit.samplefloat.injection.module.ApplicationModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BoilerplateApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new Crashlytics());
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
////        Realm.deleteRealm(config);
//        Realm.setDefaultConfiguration(config);
    }

    public static BoilerplateApplication get(Context context) {
        return (BoilerplateApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
