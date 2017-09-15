package com.ygst.cenggeche.ui.activity.notice;

import com.ygst.cenggeche.bean.notice.NoticeListBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NoticeContract {
    interface View extends BaseView {

        void getNoticeListSuccess(NoticeListBean noticeListBean);
        void getNoticeListError();
    }

    interface  Presenter extends BasePresenter<View> {

        void getNoticeList();
    }
}
