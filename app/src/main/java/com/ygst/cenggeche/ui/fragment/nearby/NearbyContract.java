package com.ygst.cenggeche.ui.fragment.nearby;

import com.ygst.cenggeche.bean.NearByBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NearbyContract {
    interface View extends BaseView {

        void getnearbySuccess(NearByBean nearbybean);
        void getnearbyFail(String msg);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void getnearBy(String lit, String lat, int page);
    }
}
