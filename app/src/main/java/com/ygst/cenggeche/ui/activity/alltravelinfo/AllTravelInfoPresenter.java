package com.ygst.cenggeche.ui.activity.alltravelinfo;

import android.content.Context;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.bean.UserBean;
import com.ygst.cenggeche.bean.UserDetailsInfoBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
                    LogUtils.i(TAG, "onNext:+ ++++++++++++++" +s);

                    Gson gson = new Gson();
                    UserDetailsInfoBean userBean = gson.fromJson(s, UserDetailsInfoBean.class);

                    if ("0000".equals(userBean.getCode())) {
                        if (mView != null)
                            mView.getUserInfoSuccess(userBean);

//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                    } else {
                        if (mView != null)
                            mView.getUserInfoFail();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                    }
                }
            }, map);
    }

    @Override
    public void checkApplyPeerInfo(String userFlag, String sid) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", userFlag);
        map.put("sid", sid);
        HttpManager.getHttpManager().postMethod(UrlUtils.CHECKUSERAPPLYSTATUS, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
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
                    mView.getcheckInfoSuccess(codeBean);
                } else {
                    mView.getcheckInfoFail(codeBean.getMsg());
                    ToastUtil.show(mView.getContext(),codeBean.getMsg());
                }


            }
        }, map);
    }
}
