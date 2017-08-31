package com.ygst.cenggeche.ui.activity.register;

import android.app.ProgressDialog;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {

    @Override
    public void checkIsRegist(String username) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "检测手机号是否可用");
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        HttpManager.getHttpManager().postMethod(UrlUtils.CHECK_IS_REGIST, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
            }

            @Override
            public void onNext(String s) {
                progressDialog.dismiss();
                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);
                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null) {
                        mView.checkIsRegistSuccess();
                    }
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                } else {
                    if (mView != null) {
                        mView.checkIsRegistError();
                    }
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }

    @Override
    public void getSMSCode(String phone) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_SMS_CODE, new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("checkSMSCodeError", "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                Log.i("checkSMSCodeError", "onNext:+ ++++++++++++++" + s);
                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);
                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null)
                        mView.getSMSCodeSuccess(codeBean);
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                } else {
                    if (mView != null)
                        mView.getSMSCodeError();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }

    @Override
    public void checkSMSCode(String username, String smsCode) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "正在校验");
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("smsCode", smsCode);
        HttpManager.getHttpManager().postMethod(UrlUtils.CHECK_SMS_CODE, new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                if (mView != null)
                    mView.checkSMSCodeError();
                Log.i("checkSMSCodeError", "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                progressDialog.dismiss();
                Log.i("checkSMSCodeError", "onNext:+ ++++++++++++++" + s);
//                LoginBean loginBean = (LoginBean) GsonManger.getGsonManger().gsonFromat(s, new LoginBean());

                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);

                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null)
                        mView.checkSMSCodeSuccess(codeBean);
                } else {
                    if (mView != null)
                        mView.checkSMSCodeError();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }
}
