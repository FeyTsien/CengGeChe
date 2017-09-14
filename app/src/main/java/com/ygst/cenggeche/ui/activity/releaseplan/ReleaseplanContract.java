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

        //获取发布信息
        void releaseSuccess(AllStrokeBean allStrokeBean);
        void releaseFail(String fail);
//        //检测发布状态
//        void checkuserstatusSuccess(String code);
//        void checkuserFail(String fail);


        
    }

    interface  Presenter extends BasePresenter<View> {
        void releaseStroke(String type, String startAddr, String endAddr, String startTime, String endLoca, String brand, String startLoca, String color,String userid);

//        void getuserStatus();
    }


}
