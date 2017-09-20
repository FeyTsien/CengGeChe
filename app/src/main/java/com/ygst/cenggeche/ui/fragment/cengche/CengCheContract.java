package com.ygst.cenggeche.ui.fragment.cengche;

import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CengCheContract {
    interface View extends BaseView {
        void getAllInfoSuccess(AllStrokeBean allStrokeBean);
        void getAllInfoFail();

        //检测是否可以拼车
        void checkApplySuccess(String msg);
        void checkApplyFail(String msg);

        //        检测用户是否可申请同行接口
        void checkApplyPeerSuccess(CheckPeerBean checkPeerBean);
        void checkApplyPeerFail(String msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getAllinfo(String type, int page);

        void checkApplyinfo();

        void  checkApplyPeerInfo(String userFlag,String id);

    }
}
