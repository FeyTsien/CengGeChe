package com.ygst.cenggeche.ui.activity.changecity;

import android.content.Context;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChangeCityContract {
    interface View extends BaseView {

        void getcitysuccess();
        void getcityfail();
        
    }

    interface  Presenter extends BasePresenter<View> {

        void getcityname();
        
    }
}
