package com.ygst.cenggeche.ui.activity.alltravelinfo;

import android.content.Context;

import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.bean.UserDetailsInfoBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AllTravelInfoContract {
    interface View extends BaseView {

        void getUserInfoSuccess(UserDetailsInfoBean userDetailsInfoBean);
        void getUserInfoFail();

        void getcheckInfoSuccess(CheckPeerBean checkPeerBean);
        void getcheckInfoFail(String msg);


    }

    interface  Presenter extends BasePresenter<View> {

        void getUserInfo(String sid);

        void  checkApplyPeerInfo(String userFlag,String id);

    }
}
