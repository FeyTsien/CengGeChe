package com.ygst.cenggeche.ui.activity.newfriendlist;

import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.ApplyBean;
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

public class NewFriendListPresenter extends BasePresenterImpl<NewFriendListContract.View> implements NewFriendListContract.Presenter{

    private final String TAG = "NewFriendListPresenter";
    @Override
    public void getApplyList(String username) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", username);
        HttpManager.getHttpManager().postMethod(UrlUtils.APPLY_LIST, new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("checkSMSCodeError", "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                Log.i("checkSMSCodeError", "onNext:+ ++++++++++++++" + s);
//                LoginBean loginBean = (LoginBean) GsonManger.getGsonManger().gsonFromat(s, new LoginBean());

                Gson gson = new Gson();
                ApplyBean applyBean = gson.fromJson(s, ApplyBean.class);

                if ("0000".equals(applyBean.getCode())) {
                    if (mView != null)
                        mView.getApplyListSuccess(applyBean);
                } else {
                    if (mView != null)
                        mView.getApplyListError();
                    LogUtils.i(TAG,"code:"+applyBean.getCode());
                }
            }
        }, map);

    }

    @Override
    public void deleteDate(ApplyBean.DataBean dataBean, int position) {

    }
}
