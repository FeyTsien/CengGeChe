package com.ygst.cenggeche.ui.activity.friendblacklist;

import com.ygst.cenggeche.bean.FriendListBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FriendBlackListContract {
    interface View extends BaseView {
        void getBlackListSuccess(FriendListBean friendListBean);
        void getBlackListError();
    }

    interface  Presenter extends BasePresenter<View> {
        void getBlackList(String myusername);
    }
}
