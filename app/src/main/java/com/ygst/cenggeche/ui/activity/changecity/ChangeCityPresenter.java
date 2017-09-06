package com.ygst.cenggeche.ui.activity.changecity;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

import static com.ygst.cenggeche.utils.ChString.type;
import static com.ygst.cenggeche.utils.UrlUtils.GETCITYNAME;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChangeCityPresenter extends BasePresenterImpl<ChangeCityContract.View> implements ChangeCityContract.Presenter{

    private String TAG=this.getClass().getSimpleName();
    @Override
    public void getcityname() {


        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "获取信息");
        Map<String, String> map = new HashMap<>();

        HttpManager.getHttpManager().postMethod(UrlUtils.BASEURl+ UrlUtils.GETCITYNAME, new Observer<String>() {

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
                if ("0000".equals(allStrokeBean.getCode())) {

                    //已注册，可以登录
                    if (mView != null) {
                        mView.getcitysuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.getcityfail();
                    }
                }
            }
        }, map);

    }
}
