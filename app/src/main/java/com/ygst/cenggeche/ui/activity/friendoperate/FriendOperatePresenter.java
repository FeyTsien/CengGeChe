package com.ygst.cenggeche.ui.activity.friendoperate;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
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

public class FriendOperatePresenter extends BasePresenterImpl<FriendOperateContract.View> implements FriendOperateContract.Presenter {

    private String TAG = "FriendOperatePresenter";

    @Override
    public void delFriend(String myusername, String targetUsername) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        map.put("targetUsername", targetUsername);
        HttpManager.getHttpManager().postMethod(UrlUtils.DELETE_FRIEND, new Observer<String>() {

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
                        mView.delFriendSuccess();
                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                } else {
                    if (mView != null)
                        mView.delFriendError();
                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }

    @Override
    public void addBlackList(String myusername, String targetUsername) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        map.put("targetUsername", targetUsername);
        HttpManager.getHttpManager().postMethod(UrlUtils.ADD_BLACKLIST, new Observer<String>() {

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
                        mView.addBlackListSuccess();
                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                } else {
                    if (mView != null)
                        mView.addBlackListError();
                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }

    @Override
    public void removeBlackList(String myusername, String targetUsername) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        map.put("targetUsername", targetUsername);
        HttpManager.getHttpManager().postMethod(UrlUtils.REMOVE_BLACKLIST, new Observer<String>() {

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
                        mView.removeBlackListSuccess();
                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                } else {
                    if (mView != null)
                        mView.removeBlackListError();
                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }
}
