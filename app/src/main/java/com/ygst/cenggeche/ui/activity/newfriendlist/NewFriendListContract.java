package com.ygst.cenggeche.ui.activity.newfriendlist;

import com.ygst.cenggeche.bean.ApplyBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewFriendListContract {
    interface View extends BaseView {
        void getApplyListSuccess(ApplyBean applyBean);
        void getApplyListError();
    }

    interface  Presenter extends BasePresenter<View> {
        void getApplyList(String username);

        void deleteDate(ApplyBean.DataBean dataBean, int position);
    }
}
