package com.ygst.cenggeche.ui.fragment.me;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MeContract {
    interface View extends BaseView {
        void loginOutSuccess();
        void loginOutError();
    }

    interface  Presenter extends BasePresenter<View> {
        void loginOut();
    }
}
