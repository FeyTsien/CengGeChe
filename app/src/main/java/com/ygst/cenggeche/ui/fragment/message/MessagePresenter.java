package com.ygst.cenggeche.ui.fragment.message;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.notice.NoticeHeadBean;
import com.ygst.cenggeche.bean.systemNotify.SystemNotityHeadBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessagePresenter extends BasePresenterImpl<MessageContract.View> implements MessageContract.Presenter{

    private String TAG = "MessagePresenter";
    @Override
    public void deleteConversation(Conversation conversation, int position) {
        Boolean deleteBoolean;
        if (conversation.getType().equals(ConversationType.single)){
            String targetId = ((UserInfo) conversation.getTargetInfo()).getUserName();
            deleteBoolean = JMessageClient.deleteSingleConversation(targetId, conversation.getTargetAppKey());
            if (deleteBoolean) {
                mView.getDeleteConversationSuccess(ConversationType.single,position);
            }else{
                mView.getDeleteConversationError();
            }
        }else{
            long groupID = ((GroupInfo) conversation.getTargetInfo()).getGroupID();
            deleteBoolean =JMessageClient.deleteGroupConversation(groupID);
            if (deleteBoolean) {
                mView.getDeleteConversationSuccess(ConversationType.group,position);
            }else{
                mView.getDeleteConversationError();
            }

        }
    }

    @Override
    public void getsystemInformHead() {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_SYSTEM_NOTIFY_HEAD, new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);

                Gson gson = new Gson();
                SystemNotityHeadBean systemNotityHeadBean = gson.fromJson(s, SystemNotityHeadBean.class);

                if ("0000".equals(systemNotityHeadBean.getCode())) {
                    if (mView != null)
                        mView.getsystemInformHeadSuccess(systemNotityHeadBean);
                } else {
                    if (mView != null)
                        mView.getsystemInformHeadError();
                }
            }
        }, map);
    }

    @Override
    public void getNoticeHead() {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_NOTICE_HEAD, new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);

                Gson gson = new Gson();
                NoticeHeadBean noticeHeadBean = gson.fromJson(s, NoticeHeadBean.class);

                if ("0000".equals(noticeHeadBean.getCode())) {
                    if (mView != null)
                        mView.getNoticeHeadSuccess(noticeHeadBean);
                } else {
                    if (mView != null)
                        mView.getNoticeHeadError();
                }
            }
        }, map);
    }
}
