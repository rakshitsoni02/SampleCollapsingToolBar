package in.co.rakshit.samplefloat.ui.main;

import android.util.Log;

import javax.inject.Inject;

import in.co.rakshit.samplefloat.data.model.Base;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import in.co.rakshit.samplefloat.data.DataManager;
import in.co.rakshit.samplefloat.injection.ConfigPersistent;
import in.co.rakshit.samplefloat.ui.base.BasePresenter;
import in.co.rakshit.samplefloat.util.RxUtil;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadBusiness() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        if (mDataManager.getBusinessFromPref() == null) {
            mSubscription = mDataManager.getBusiness()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Base>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "There was an error loading the ribots.");
                            getMvpView().showError();
                        }

                        @Override
                        public void onNext(Base value) {
                            if (value == null) {
                                getMvpView().showRibotsEmpty();
                            } else {
                                mDataManager.setBusinessInPref(value);
                                getMvpView().showRibots(value);
                            }
                        }
                    });
        } else{
            Log.e("else","else");
            getMvpView().showRibots(mDataManager.getBusinessFromPref());
        }
    }
}
