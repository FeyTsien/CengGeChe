package com.ygst.cenggeche.ui.activity.resetpwd;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ResetPwdContract {
    interface View extends BaseView {
        void resetPwdSuccess();
        void resetPwdError();
    }

    interface  Presenter extends BasePresenter<View> {

        void resetPwd(String username,String password);
    }
}
