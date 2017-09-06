package com.ygst.cenggeche.ui.fragment.cengche;

import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CengCheContract {
    interface View extends BaseView {
        void getAllInfoSuccess(AllStrokeBean allStrokeBean);
        void getAllInfoFail();

    }

    interface  Presenter extends BasePresenter<View> {
        void getAllinfo(String type, int page);
    }
}
