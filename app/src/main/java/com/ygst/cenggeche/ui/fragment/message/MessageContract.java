package com.ygst.cenggeche.ui.fragment.message;

import com.ygst.cenggeche.bean.notice.NoticeHeadBean;
import com.ygst.cenggeche.bean.systemNotify.SystemNotityHeadBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessageContract {
    interface View extends BaseView {

        void getDeleteConversationSuccess(ConversationType type, int position);

        void getDeleteConversationError();

        void getsystemInformHeadSuccess(SystemNotityHeadBean systemNotityHeadBean);

        void getsystemInformHeadError();

        void getNoticeHeadSuccess(NoticeHeadBean noticeHeadBean);

        void getNoticeHeadError();

    }

    interface Presenter extends BasePresenter<View> {

        void deleteConversation(Conversation conversation, int position);

        void getsystemInformHead();

        void getNoticeHead();

    }
}
