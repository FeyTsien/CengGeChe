package com.ygst.cenggeche.ui.fragment.me;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.MyInfoBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MePresenter extends BasePresenterImpl<MeContract.View> implements MeContract.Presenter{

    private String TAG = "MePresenter";

    @Override
    public void getMyInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_MY_INFO, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                LogUtils.i(TAG, "ssss:" + s);
                Gson gson = new Gson();
                MyInfoBean myInfoBean = gson.fromJson(s, MyInfoBean.class);
                if ("0000".equals(myInfoBean.getCode())) {
                    if (mView != null) {
                        mView.getMyInfoSuccess(myInfoBean);
                    }
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                } else {
                    if (mView != null) {
                        mView.getMyInfoError();
                    }
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }
}
