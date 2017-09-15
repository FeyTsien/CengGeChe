package com.ygst.cenggeche.ui.activity.main;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.NewAppVersionBean;
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

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{

    private String TAG = "MainPresenter";

    @Override
    public void getNewAppVersion() {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_NEW_APP_VERSION, new Observer<String>() {

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
                NewAppVersionBean newAppVersionBean = gson.fromJson(s, NewAppVersionBean.class);

                if ("0000".equals(newAppVersionBean.getCode())) {
                    if (mView != null)
                        mView.getNewAppVersionSuccess(newAppVersionBean);
                } else {
                    LogUtils.i(TAG,"code:"+ newAppVersionBean.getCode());
                }
            }
        }, map);
    }
}
