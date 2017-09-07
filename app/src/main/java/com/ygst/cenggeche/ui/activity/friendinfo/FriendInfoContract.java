package com.ygst.cenggeche.ui.activity.friendinfo;

import com.ygst.cenggeche.bean.UserBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FriendInfoContract {
    interface View extends BaseView {
        void getFriendInfoSuccess(UserBean userBean);
        void getFriendInfoError();
    }

    interface  Presenter extends BasePresenter<View> {
        void getFriendInfo(String friendsUsername);
    }
}
