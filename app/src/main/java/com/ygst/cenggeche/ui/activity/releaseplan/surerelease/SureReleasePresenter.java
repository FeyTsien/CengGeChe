package com.ygst.cenggeche.ui.activity.releaseplan.surerelease;

import android.app.ProgressDialog;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

import static com.ygst.cenggeche.utils.ChString.type;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SureReleasePresenter extends BasePresenterImpl<SureReleaseContract.View> implements SureReleaseContract.Presenter{


    private String TAG=this.getClass().getSimpleName();
    @Override
    public void getSureRelease(String sid, String comments, String userId, String usid) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "获取信息");
        Map<String, String> map = new HashMap<>();
        map.put("sid", sid);
        map.put("comments", comments);
        map.put("userId", userId);
        map.put("usid", usid);


        HttpManager.getHttpManager().postMethod(UrlUtils.BASEURl+ UrlUtils.RELEASESTROKE, new Observer<String>() {

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
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);
                String msg = codeBean.getMsg();
                if ("0000".equals(codeBean.getCode())) {
                    //已注册，可以登录
                    if (mView != null) {
                        mView.sureReleaseSuccess();
                    }
                } else {
                    if (mView != null) {
                        mView.sureReleaseFail(msg);
                    }
                }
            }
        }, map);
    }
}
