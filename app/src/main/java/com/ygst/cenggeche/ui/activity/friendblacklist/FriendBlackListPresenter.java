package com.ygst.cenggeche.ui.activity.friendblacklist;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.FriendListBean;
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

public class FriendBlackListPresenter extends BasePresenterImpl<FriendBlackListContract.View> implements FriendBlackListContract.Presenter {

    private String TAG = "FriendBlackListPresenter";

    @Override
    public void getBlackList(String myusername) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        HttpManager.getHttpManager().postMethod(UrlUtils.BLACK_LIST, new Observer<String>() {

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
                        mView.getBlackListSuccess(friendListBean);
                } else {
                    if (mView != null) {
                        mView.getBlackListError();
                        ToastUtil.show(mView.getContext(), friendListBean.getMsg());
                    }
                    LogUtils.i(TAG, "code:" + friendListBean.getCode());
                }
            }
        }, map);
    }
}
