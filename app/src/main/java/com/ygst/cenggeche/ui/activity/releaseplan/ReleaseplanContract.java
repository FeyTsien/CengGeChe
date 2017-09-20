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


//        //检测发布状态
        void checkuserstatusSuccess(String code);
        void checkuserFail(String fail);


        
    }

    interface  Presenter extends BasePresenter<View> {

        void getuserStatus();
    }


}
