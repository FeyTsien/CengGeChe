package com.ygst.cenggeche.ui.activity.peerrequest.chooseimg;

import android.content.Context;

import com.ygst.cenggeche.bean.ChoosePicBean;
import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChooseImgContract {
    interface View extends BaseView {
        void getUserPicSuccess(ChoosePicBean choosePicBean);
        void getUserPicFail();
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserPic();
    }
}
