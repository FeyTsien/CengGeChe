package com.ygst.cenggeche.ui.activity.friendlist;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.FriendListBean;
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

public class FriendListPresenter extends BasePresenterImpl<FriendListContract.View> implements FriendListContract.Presenter{

    private String TAG = "FriendListPresenter";
    @Override
    public void getFriendList(String myusername) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        HttpManager.getHttpManager().postMethod(UrlUtils.FRIEND_LIST, new Observer<String>() {

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
                FriendListBean friendListBean = gson.fromJson(s, FriendListBean.class);

                if ("0000".equals(friendListBean.getCode())) {
                    if (mView != null)
                        mView.getFriendListSuccess(friendListBean);
                } else {
                    if (mView != null)
                        mView.getFriendListError();
                    LogUtils.i(TAG,"code:"+ friendListBean.getCode());
                }
            }
        }, map);
    }
}
