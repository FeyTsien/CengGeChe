package com.ygst.cenggeche.ui.activity.systemnotify;

import com.ygst.cenggeche.bean.systemNotify.SystemNotityListBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SystemNotifyContract {
    interface View extends BaseView {
        void getSystemNotifyListSuccess(SystemNotityListBean systemNotityListBean);
        void getSystemNotifyListError();
    }

    interface  Presenter extends BasePresenter<View> {

        void getSystemNotifyList();
    }
}
