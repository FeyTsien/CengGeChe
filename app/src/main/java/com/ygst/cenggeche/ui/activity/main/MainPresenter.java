package com.ygst.cenggeche.ui.activity.main;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
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

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{

    private String TAG = "MainPresenter";
    @Override
    public void weAreFriend(String myusername, String fusername) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        map.put("friendusername", fusername);
        HttpManager.getHttpManager().postMethod(UrlUtils.APPLY_DATE_YES_AGREE, new Observer<String>() {
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
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);

                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null)
                        mView.weAreFriendSuccess();
                } else {
                    if (mView != null)
                        mView.weAreFriendError();
                    LogUtils.i(TAG,"code:"+codeBean.getCode());
                }
            }
        }, map);
    }
}
