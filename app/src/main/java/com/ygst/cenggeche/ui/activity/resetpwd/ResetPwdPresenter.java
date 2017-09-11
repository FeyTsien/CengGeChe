package com.ygst.cenggeche.ui.activity.resetpwd;

import android.app.ProgressDialog;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.MD5Util;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ResetPwdPresenter extends BasePresenterImpl<ResetPwdContract.View> implements ResetPwdContract.Presenter{

    private static String TAG = "ResetPwdPresenter";
    @Override
    public void resetPwd(String username,String password) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "正在重置密码。。。");
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", MD5Util.string2MD5(password));
        HttpManager.getHttpManager().postMethod(UrlUtils.RESET_PWD, new Observer<String>() {
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
//                LoginBean loginBean = (LoginBean) GsonManger.getGsonManger().gsonFromat(s, new LoginBean());

                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);

                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null)
                        mView.resetPwdSuccess();
                } else {
                    if (mView != null)
                        mView.resetPwdError();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }
}
