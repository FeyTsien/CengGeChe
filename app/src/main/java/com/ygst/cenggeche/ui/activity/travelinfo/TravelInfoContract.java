package com.ygst.cenggeche.ui.activity.travelinfo;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TravelInfoContract {
    interface View extends BaseView {
        void gettravelinfosuccess();
        void gettravelfail(String msg);
    }

    interface  Presenter extends BasePresenter<View> {

        void gettravelinfo();
    }
}
