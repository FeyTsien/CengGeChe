package com.ygst.cenggeche.ui.fragment.me;

import com.ygst.cenggeche.bean.MyInfoBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MeContract {
    interface View extends BaseView {
        void getMyInfoSuccess(MyInfoBean myInfoBean);
        void getMyInfoError();
        void loginOutSuccess();
        void loginOutError();
    }

    interface  Presenter extends BasePresenter<View> {
        void getMyInfo();
        void loginOut();
    }
}
