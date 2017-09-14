package com.ygst.cenggeche.ui.activity.travelinfo;

import com.ygst.cenggeche.bean.NowTravelInfoBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TravelInfoContract {
    interface View extends BaseView {
        //得到行程的信息
        void gettravelinfosuccess(NowTravelInfoBean nowTravelInfoBean);
        void gettravelfail(String msg);

        //改变行程状态
        void changeInfoSuccess();
        void changeInfoFail();
    }

    interface  Presenter extends BasePresenter<View> {

        void gettravelinfo();

        void changeInfo(String userFlag,String csid,String usid,String strokeStatus);
    }
}
