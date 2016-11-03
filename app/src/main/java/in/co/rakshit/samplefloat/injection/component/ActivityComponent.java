package in.co.rakshit.samplefloat.injection.component;

import dagger.Subcomponent;
import in.co.rakshit.samplefloat.injection.PerActivity;
import in.co.rakshit.samplefloat.injection.module.ActivityModule;
import in.co.rakshit.samplefloat.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
