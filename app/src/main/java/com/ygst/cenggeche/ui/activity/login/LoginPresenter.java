package com.ygst.cenggeche.ui.activity.login;

import android.app.ProgressDialog;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.bean.LoginBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private static String TAG = "LoginPresenter";

    //校验用户是否注册
    @Override
    public void checkIsRegist(String username) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "检查账号");
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
                LogUtils.i(TAG, "ssss:" + s);
                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);
                if ("0002".equals(codeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.registered();
                    }
                } else {
                    if (mView != null) {
                        mView.unregistered();
                    }
                }
            }
        }, map);
    }

    //  获取验证码
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
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);
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

    // 登录
    @Override
    public void login(Map<String, String> map) {

        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "正在登录");
        HttpManager.getHttpManager().postMethod(UrlUtils.LOGIN, new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                progressDialog.dismiss();
                LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);
//                LoginBean loginBean = (LoginBean) GsonManger.getGsonManger().gsonFromat(s, new LoginBean());

                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(s, LoginBean.class);

                if ("0000".equals(loginBean.getCode())) {
                    if (mView != null)
                        mView.loginSuccess(loginBean);
                } else {
                    if (mView != null)
                        mView.loginError();
                    ToastUtil.show(mView.getContext(), loginBean.getMsg());
                }
            }
        }, map);

    }
}
