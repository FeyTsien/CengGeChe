package com.ygst.cenggeche.ui.activity.releaseplan;

import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ReleaseplanContract {
    interface View extends BaseView {

        void releaseSuccess(AllStrokeBean allStrokeBean);
        void releaseFail(String fail);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void releaseStroke(String type, String startAddr, String endAddr, String startTime, String endLoca, String brand, String startLoca, String color,String userid);
    }


}
