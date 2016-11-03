package in.co.rakshit.samplefloat.ui.main;

import in.co.rakshit.samplefloat.data.model.Base;
import in.co.rakshit.samplefloat.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(Base ribots);

    void showRibotsEmpty();

    void showError();

}
