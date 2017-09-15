package com.ygst.cenggeche.ui.fragment.cengche;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CengChePresenter extends BasePresenterImpl<CengCheContract.View> implements CengCheContract.Presenter {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void getAllinfo(String type, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", type);
        map.put("page", page + "");

        HttpManager.getHttpManager().postMethod(UrlUtils.ALLTRAVEL, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.show(mView.getContext(), "请求失败，请重试");
                LogUtils.e(TAG, "返回的onError", e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                AllStrokeBean codeBean = gson.fromJson(s, AllStrokeBean.class);
                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null) {
                        mView.getAllInfoSuccess(codeBean);
                    }
                } else {
                    if (mView != null) {
                        mView.getAllInfoFail();
                    }
                }
            }
        }, map);
    }

    @Override
    public void checkApplyinfo(String userFlag, String sid) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", userFlag);
        map.put("sid", sid + "");
        HttpManager.getHttpManager().postMethod(UrlUtils.GETSTROKEUSERINFO, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                ToastUtil.show(mView.getContext(), "请求失败，请重试");
                LogUtils.e(TAG, "返回的onError", e);
            }

            @Override
            public void onNext(String s) {

                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);
                if ("0000".equals(codeBean.getCode())) {
                    mView.checkApplySuccess();
                } else {
                    mView.checkApplyFail(codeBean.getMsg());
                }
            }
        }, map);
    }
}
