package com.ygst.cenggeche.ui.activity.travelinfo;

import android.app.ProgressDialog;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.bean.NowTravelInfoBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

import static com.ygst.cenggeche.utils.ChString.type;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TravelInfoPresenter extends BasePresenterImpl<TravelInfoContract.View> implements TravelInfoContract.Presenter{
    private String TAG=this.getClass().getSimpleName();

    @Override
    public void gettravelinfo() {
        Map<String, String> map = new HashMap<>();
        map.put("","");
        HttpManager.getHttpManager().postMethod(UrlUtils.GETCURRENTSTROKE, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(TAG, "返回的onError", e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.i("HttpManager", "ssss:" + s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String userName = data.getString("userName");
                    LogUtils.i("HttpManager", "userName:" + userName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String userName = allStrokeBean.getData().getUserName();

                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.gettravelinfosuccess(allStrokeBean);
                    }
                } else {
                    if (mView != null) {
                        mView.gettravelfail(msg);
                    }
                }
            }
        }, map);
    }

    //改变状态
    @Override
    public void changeInfo(String userFlag,String csid,String usid,String strokeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag",userFlag);
        map.put("csid",csid);
        map.put("usid",usid);
        map.put("strokeStatus",strokeStatus);

        HttpManager.getHttpManager().postMethod(UrlUtils.CHANGEUSERINFO, new Observer<String>() {

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
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {

                    //已注册，可以登录
                    if (mView != null) {
                        mView.changeInfoSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.changeInfoFail();
                    }
                }
            }
        }, map);
    }


    //确认到达
    @Override
    public void changeInfoArrive(String userFlag, String csid, String usid, String strokeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag",userFlag);
        map.put("csid",csid);
        map.put("usid",usid);
        map.put("strokeStatus",strokeStatus);

        HttpManager.getHttpManager().postMethod(UrlUtils.CHANGEUSERINFO, new Observer<String>() {

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
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.changeInfoArriveSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.changeInfoArriveFail();
                    }
                }
            }
        }, map);
    }

    //车主取消
    @Override
    public void changeInfoCarerCancel(String userFlag, String csid, String usid, String strokeStatus) {
            Map<String, String> map = new HashMap<>();
            map.put("userFlag",userFlag);
            map.put("csid",csid);
            map.put("usid",usid);
            map.put("strokeStatus",strokeStatus);

        HttpManager.getHttpManager().postMethod(UrlUtils.CHANGEUSERINFO, new Observer<String>() {

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
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.changeInfoCarerCancelSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.changeInfoCarerCancelFail();
                    }
                }
            }
        }, map);

    }


    @Override
    public void changeInfoIgnoreCancel(String userFlag, String csid, String usid, String strokeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag",userFlag);
        map.put("csid",csid);
        map.put("usid",usid);
        map.put("strokeStatus",strokeStatus);

        HttpManager.getHttpManager().postMethod(UrlUtils.CHANGEUSERINFO, new Observer<String>() {

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
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.changeInfoCarerIgnoreSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.changeInfoCarerIgnoreFail();
                    }
                }
            }
        }, map);

    }

    //车主接受
    @Override
    public void changeInfoCarerAgree(String userFlag, String csid, String usid, String strokeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag",userFlag);
        map.put("csid",csid);
        map.put("usid",usid);
        map.put("strokeStatus",strokeStatus);

        HttpManager.getHttpManager().postMethod(UrlUtils.CHANGEUSERINFO, new Observer<String>() {

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
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.changeInfoCarerAgreeSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.changeInfoCarerAgreeFail();
                    }
                }
            }
        }, map);
    }

    //车主确定上车
    @Override
    public void changeInfoCarerSureUpCar(String userFlag, String csid, String usid, String strokeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag",userFlag);
        map.put("csid",csid);
        map.put("usid",usid);
        map.put("strokeStatus",strokeStatus);

        HttpManager.getHttpManager().postMethod(UrlUtils.CHANGEUSERINFO, new Observer<String>() {
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
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.changeInfoCarerSureUpCarSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.changeInfoCarerSureUpCarFail();
                    }
                }
            }
        }, map);
    }

    //车主确定行程结束
    @Override
    public void changeInfoCarerSureOver(String userFlag, String csid, String usid, String strokeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userFlag",userFlag);
        map.put("csid",csid);
        map.put("usid",usid);
        map.put("strokeStatus",strokeStatus);

        HttpManager.getHttpManager().postMethod(UrlUtils.CHANGEUSERINFO, new Observer<String>() {

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
                NowTravelInfoBean allStrokeBean = gson.fromJson(s, NowTravelInfoBean.class);
                String msg = allStrokeBean.getMsg();
                if ("0000".equals(allStrokeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.changeInfoCarerSureOverSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.changeInfoCarerSureOverFail();
                    }
                }
            }
        }, map);
    }
}
