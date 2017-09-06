package com.ygst.cenggeche.ui.activity.releaseplan.surerelease;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SureReleaseContract {
    interface View extends BaseView {
        void sureReleaseSuccess();
        void sureReleaseFail(String msg);
    }

    interface  Presenter extends BasePresenter<View> {
         void getSureRelease(String type, String startAddr, String endAddr, String startTime);
    }
}
