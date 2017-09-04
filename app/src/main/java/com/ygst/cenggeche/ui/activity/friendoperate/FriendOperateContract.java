package com.ygst.cenggeche.ui.activity.friendoperate;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FriendOperateContract {
    interface View extends BaseView {
        void delFriendSuccess();
        void delFriendError();

        void addBlackListSuccess();
        void addBlackListError();

        void removeBlackListSuccess();
        void removeBlackListError();
    }

    interface  Presenter extends BasePresenter<View> {

        //删除好友
        void delFriend(String myusername,String targetUsername);
        //将好友加入黑名单
        void addBlackList(String myusername,String targetUsername);
        //将好友移除黑名单
        void removeBlackList(String myusername,String targetUsername);
    }
}
