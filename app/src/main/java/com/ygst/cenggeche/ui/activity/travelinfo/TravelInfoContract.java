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

        //改变确认到达
        void changeInfoArriveSuccess();
        void changeInfoArriveFail();

        //车主取消同行
        void changeInfoCarerCancelSuccess();
        void changeInfoCarerCancelFail();

        //车主忽略行程
        void changeInfoCarerIgnoreSuccess();
        void changeInfoCarerIgnoreFail();

        //车主接受行程
        void changeInfoCarerAgreeSuccess();
        void changeInfoCarerAgreeFail();

        //车主确定上车行程
        void changeInfoCarerSureUpCarSuccess();
        void changeInfoCarerSureUpCarFail();

        //车主确定上车行程结束
        void changeInfoCarerSureOverSuccess();
        void changeInfoCarerSureOverFail();



    }

    interface  Presenter extends BasePresenter<View> {

        void gettravelinfo();
        void changeInfo(String userFlag,String csid,String usid,String strokeStatus);
        void changeInfoArrive(String userFlag,String csid,String usid,String strokeStatus);


        void changeInfoIgnoreCancel(String userFlag,String csid,String usid,String strokeStatus);
        void changeInfoCarerCancel(String userFlag,String csid,String usid,String strokeStatus);
        void changeInfoCarerAgree(String userFlag,String csid,String usid,String strokeStatus);
        void changeInfoCarerSureUpCar(String userFlag,String csid,String usid,String strokeStatus);
        void changeInfoCarerSureOver(String userFlag,String csid,String usid,String strokeStatus);



    }
}
