package in.co.rakshit.samplefloat.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.co.rakshit.samplefloat.data.model.Base;
import in.co.rakshit.samplefloat.data.remote.LookUpService;
import rx.Observable;
import in.co.rakshit.samplefloat.data.local.PreferencesHelper;

@Singleton
public class DataManager {
    private final LookUpService mLookUpService;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(LookUpService lookUpService, PreferencesHelper preferencesHelper
    ) {
        mLookUpService = lookUpService;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Base> syncRibots() {
        return null;
    }

    public Observable<Base> getBusiness() {
        return mLookUpService.getData();
    }

    public Base getBusinessFromPref() {
        return mPreferencesHelper.getBussiness();
    }

    public void setBusinessInPref(Base base) {
        mPreferencesHelper.setBussiness(base);
    }
}
