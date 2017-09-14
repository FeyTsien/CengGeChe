package com.ygst.cenggeche.ui.activity.releaseplan;

import android.app.ProgressDialog;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.bean.CodeBean;
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

import static com.amap.api.mapcore.util.ij.a;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ReleaseplanPresenter extends BasePresenterImpl<ReleaseplanContract.View> implements ReleaseplanContract.Presenter{
    private String TAG=this.getClass().getSimpleName();


    @Override
    public void releaseStroke(String type,String startAddr,String endAddr,String startTime,String endLoca,String brand,String startLoca,String color,String userId) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "获取信息");
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", type);
        map.put("startAddr", startAddr);
        map.put("endAddr", endAddr);
        map.put("startTime", startTime);
        map.put("startLoca", startLoca);
        map.put("endLoca", endLoca);
        map.put("brand", brand);
        map.put("color", color);
        map.put("userId", userId);

        Log.i(TAG, UrlUtils.BASEURl+ UrlUtils.ALLTRAVEL);
        HttpManager.getHttpManager().postMethod(UrlUtils.BASEURl+ UrlUtils.CONFIRMSTROKE, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                ToastUtil.show(mView.getContext(), "请求失败，请重试");
                LogUtils.e(TAG, "返回的onError", e);
            }

            @Override
            public void onNext(String s) {
                progressDialog.dismiss();
                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                AllStrokeBean allStrokeBean = gson.fromJson(s, AllStrokeBean.class);
                String msg = allStrokeBean.getMsg();
//                if ("0000".equals(allStrokeBean.getCode())) {
//
//                    //已注册，可以登录
//                    if (mView != null) {
//                        mView.releaseSuccess(allStrokeBean);
//                    }
//                } else {
//                    if (mView != null) {
//                        mView.releaseFail(msg);
//                    }
//                }
            }
        }, map);
    }

//    @Override
//    public void getuserStatus() {
//        Map<String, String> map = new HashMap<>();
//        map.put("s","a");
//        HttpManager.getHttpManager().postMethod(UrlUtils.BASEURl+ UrlUtils.CHECKUSERSTATUS, new Observer<String>() {
//
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ToastUtil.show(mView.getContext(), "请求失败，请重试");
//                LogUtils.e(TAG, "返回的onError", e);
//            }
//
//            @Override
//            public void onNext(String s) {
//                LogUtils.i("HttpManager", "ssss:" + s);
//                try {
//                    JSONObject jsonObject=new JSONObject(s);
//                    String code = jsonObject.getString("code");
//                    String msg = jsonObject.getString("msg");
//                    if(code.equals("0000")){
//                        String cancelNum = jsonObject.getString("cancelNum");
//                        mView.checkuserstatusSuccess(cancelNum);
//                    }else{
//                        mView.checkuserFail(msg);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Gson gson = new Gson();
////                if ("0000".equals(allStrokeBean.getCode())) {
////
////                    //已注册，可以登录
////                    if (mView != null) {
////                        mView.releaseSuccess(allStrokeBean);
////                    }
////                } else {
////                    if (mView != null) {
////                        mView.releaseFail(msg);
////                    }
////                }
//            }
//        }, map);
//    }


}
