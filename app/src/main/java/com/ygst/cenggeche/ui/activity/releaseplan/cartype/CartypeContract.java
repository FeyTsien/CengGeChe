package com.ygst.cenggeche.ui.activity.releaseplan.cartype;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CartypeContract {
    interface View extends BaseView {

        void getCarBrandSuccess();
        void getCarBrandFail();


        void getCarTypeBrandSuccess();
        void getCarTypeBrandFail();



    }

    interface  Presenter extends BasePresenter<View> {

        void getCarBrand();
        void getCarTypeBrand();
        
    }
}
