package com.ygst.cenggeche.ui.activity.systemnotify;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.systemNotify.SystemNotityListBean;
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

public class SystemNotifyPresenter extends BasePresenterImpl<SystemNotifyContract.View> implements SystemNotifyContract.Presenter{

    private String TAG = "SystemNotifyPresenter";
    @Override
    public void getSystemNotifyList() {

        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_SYSTEM_NOTIFY_LIST, new Observer<String>() {
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
                SystemNotityListBean systemNotityListBean = gson.fromJson(s, SystemNotityListBean.class);

                if ("0000".equals(systemNotityListBean.getCode())) {
                    if (mView != null)
                        mView.getSystemNotifyListSuccess(systemNotityListBean);
                } else {
                    if (mView != null)
                        mView.getSystemNotifyListError();
                }
            }
        }, map);
    }
}
