package com.ygst.cenggeche.ui.activity.addfriend;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AddFriendContract {
    interface View extends BaseView {
        void sendSucceed();
        void sendFail();
    }

    interface  Presenter extends BasePresenter<View> {
       void sendAddFriend(Map<String, String> map);
    }
}
