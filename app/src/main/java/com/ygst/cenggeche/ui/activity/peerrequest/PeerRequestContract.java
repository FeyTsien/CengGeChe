package com.ygst.cenggeche.ui.activity.peerrequest;

import android.content.Context;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PeerRequestContract {
    interface View extends BaseView {
        void sureReleaseSuccess();
        void sureReleaseFail(String msg);

        void uploadImgSuccess(String picpath);
        void uoloadFail();
    }

    interface  Presenter extends BasePresenter<View> {
        void getSureRelease(String userFlag,String usid,String userid, String sid,String startAddr, String endAddr, String startTime, String brand, String color, String strokeFlag,String comments, String filepath);

        void uploadImg(String pic, Context context);
    }
}
