package com.ygst.cenggeche.ui.activity.main;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void weAreFriendSuccess();
        void weAreFriendError();
    }

    interface  Presenter extends BasePresenter<View> {
        void weAreFriend(String myusername,String fusername);
    }
}
