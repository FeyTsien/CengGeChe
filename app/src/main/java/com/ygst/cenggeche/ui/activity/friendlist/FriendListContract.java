package com.ygst.cenggeche.ui.activity.friendlist;

import com.ygst.cenggeche.bean.FriendBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FriendListContract {
    interface View extends BaseView {
        void getFriendListSuccess(FriendBean friendBean);
        void getFriendListError();
    }

    interface  Presenter extends BasePresenter<View> {

        void getFriendList(String myusername);
    }
}
