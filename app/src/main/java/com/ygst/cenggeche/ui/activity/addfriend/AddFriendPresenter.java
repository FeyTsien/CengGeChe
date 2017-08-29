package com.ygst.cenggeche.ui.activity.addfriend;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.mvp.BasePresenterImpl;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AddFriendPresenter extends BasePresenterImpl<AddFriendContract.View> implements AddFriendContract.Presenter{
    private String TAG = "AddFriendPresenter";
    @Override
    public void sendAddFriend(String targetUserName, String targetAppKey, String reason) {
        ContactManager.sendInvitationRequest(targetUserName,targetAppKey, reason, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                LogUtils.i(TAG,"responseMessage: "+responseMessage);
                if (0 == responseCode) {
                    //好友请求请求发送成功
                    mView.sendSucceed();
                } else {
                    //好友请求发送失败
                    mView.sendFail();
                }
            }
        });
    }
}
