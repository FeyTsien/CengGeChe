package com.ygst.cenggeche.ui.activity.userinfo;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.bean.UserDetailsInfoBean;
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

public class UserInfoPresenter extends BasePresenterImpl<UserInfoContract.View> implements UserInfoContract.Presenter {

    private String TAG = "UserInfoPresenter";

    @Override
    public void getUserInfo(String username) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_STROKE_AND_USERINFO, new Observer<String>() {

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
                UserDetailsInfoBean userBean = gson.fromJson(s, UserDetailsInfoBean.class);
                if ("0000".equals(userBean.getCode())) {
                    if (mView != null)
                        mView.getUserInfoSuccess(userBean);
                } else {
                    if (mView != null)
                        mView.getUserInfoError();
                    ToastUtil.show(mView.getContext(), userBean.getMsg());
                }
            }
        }, map);
    }

    @Override
    public void checkApplyPeerInfo(final String userFlag, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", userFlag);
        map.put("sid", id);
        HttpManager.getHttpManager().postMethod(UrlUtils.CHECKUSERAPPLYSTATUS, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    ToastUtil.show(mView.getContext(), "请求失败，请重试");
                    LogUtils.e(TAG, "返回的onError", e);
                }

            }

            @Override
            public void onNext(String s) {

                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                CheckPeerBean codeBean = gson.fromJson(s, CheckPeerBean.class);
                if ("0000".equals(codeBean.getCode())) {
                    mView.getcheckInfoSuccess(codeBean,userFlag);
                } else {
                    mView.getcheckInfoFail();
                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }
}
