package com.ygst.cenggeche.ui.activity.userinfo;

import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.bean.UserDetailsInfoBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UserInfoContract {
    interface View extends BaseView {
        void getUserInfoSuccess(UserDetailsInfoBean userBean);
        void getUserInfoError();

        void getcheckInfoSuccess(CheckPeerBean checkPeerBean,String userFlag);
        void getcheckInfoFail();
    }

    interface  Presenter extends BasePresenter<View> {

        void getUserInfo(String username);
        void checkApplyPeerInfo(String userFlag,String id);
    }
}
