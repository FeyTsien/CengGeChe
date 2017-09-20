package com.ygst.cenggeche.ui.fragment.cengche;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.bean.CodeBean;
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
    public void checkApplyinfo() {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpManager.getHttpManager().postMethod(UrlUtils.CHECKUSERSTATUS, new Observer<String>() {

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
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String cancelNum = jsonObject.getString("cancelNum");
                    if ("0000".equals(codeBean.getCode())) {
                        mView.checkApplySuccess(cancelNum);
                    } else {
                        mView.checkApplyFail(codeBean.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, map);
    }


    @Override
    public void checkApplyPeerInfo(String userFlag, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", userFlag);
        map.put("sid", id);
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
                Gson gson = new Gson();
                LogUtils.i("HttpManager", s);

                CheckPeerBean codeBean = gson.fromJson(s, CheckPeerBean.class);
                if ("0000".equals(codeBean.getCode())) {
                    mView.checkApplyPeerSuccess(codeBean);
                } else {
                    mView.checkApplyPeerFail(codeBean.getMsg());
                }


            }
        }, map);
    }
}
