package com.ygst.cenggeche.ui.activity.registerinfo;

import android.app.ProgressDialog;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterInfoPresenter extends BasePresenterImpl<RegisterInfoContract.View> implements RegisterInfoContract.Presenter {

    private String TAG = "RegisterInfoPresenter";

    @Override
    public void registrationConfirm(Map<String, String> map) {

        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "正在写入注册信息");
        HttpManager.getHttpManager().postMethod(UrlUtils.REGIST, new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                progressDialog.dismiss();
                LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);
                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);
                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null)
                        mView.registrationSuccess();
                } else {
                    if (mView != null)
                        mView.registrationError();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }
}
