package com.ygst.cenggeche.ui.activity.alltravelinfo;

import android.content.Context;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.UserBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AllTravelInfoPresenter extends BasePresenterImpl<AllTravelInfoContract.View> implements AllTravelInfoContract.Presenter{

    private String TAG=this.getClass().getSimpleName();
    @Override
    public void getUserInfo(String sid) {
            Map<String, String> map = new HashMap<>();
            map.put("sid", sid);
            HttpManager.getHttpManager().postMethod(UrlUtils.GETSTROKEUSERINFO, new Observer<String>() {

                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
                }

                @Override
                public void onNext(String s) {
                    LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(s, UserBean.class);
                    if ("0000".equals(userBean.getCode())) {
                        if (mView != null)
                            mView.getUserInfoSuccess();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                    } else {
                        if (mView != null)
                            mView.getUserInfoFail();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                    }
                }
            }, map);
    }
}
