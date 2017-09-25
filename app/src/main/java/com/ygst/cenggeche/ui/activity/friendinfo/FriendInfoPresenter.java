package com.ygst.cenggeche.ui.activity.friendinfo;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.UserBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FriendInfoPresenter extends BasePresenterImpl<FriendInfoContract.View> implements FriendInfoContract.Presenter {

    private String TAG = "FriendInfoPresenter";

    @Override
    public void getFriendInfo(String friendsUsername) {
        Map<String, String> map = new HashMap<>();
        map.put("friendsUsername", friendsUsername);
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_FRIEND_INFO, new Observer<String>() {

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
                UserBean userBean = gson.fromJson(s, UserBean.class);
                if ("0000".equals(userBean.getCode())) {
                    if (mView != null)
                        mView.getFriendInfoSuccess(userBean);
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                } else {
                    if (mView != null)
                        mView.getFriendInfoError();
                    ToastUtil.show(mView.getContext(), userBean.getMsg());
                }
            }
        }, map);
    }
}
