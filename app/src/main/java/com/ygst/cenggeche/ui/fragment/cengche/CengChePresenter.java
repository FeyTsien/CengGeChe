package com.ygst.cenggeche.ui.fragment.cengche;

import android.app.ProgressDialog;

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

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CengChePresenter extends BasePresenterImpl<CengCheContract.View> implements CengCheContract.Presenter{

    private String TAG=this.getClass().getSimpleName();
    @Override
    public void getAllinfo( String type,int page) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "获取信息");
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", type);
        map.put("page", page+"");

        HttpManager.getHttpManager().postMethod(UrlUtils.BASEURl+ UrlUtils.ALLTRAVEL, new Observer<String>() {

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
}
