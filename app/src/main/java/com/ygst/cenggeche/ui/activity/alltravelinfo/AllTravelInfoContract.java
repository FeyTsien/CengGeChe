package com.ygst.cenggeche.ui.activity.alltravelinfo;

import android.content.Context;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AllTravelInfoContract {
    interface View extends BaseView {

        void getUserInfoSuccess();
        void getUserInfoFail();


    }

    interface  Presenter extends BasePresenter<View> {

        void getUserInfo(String sid);
    }
}
