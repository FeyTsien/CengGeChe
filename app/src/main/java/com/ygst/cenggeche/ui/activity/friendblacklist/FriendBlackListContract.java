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
        void removeBlackListSuccess(int position);
        void removeBlackListError();
    }

    interface  Presenter extends BasePresenter<View> {
        //获取黑名单列表
        void getBlackList(String myusername);
        //将好友移除黑名单
        void removeBlackList(String myusername,String targetUsername,int position);
    }
}
