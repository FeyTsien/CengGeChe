package com.ygst.cenggeche.ui.activity.addfriend;

import android.app.ProgressDialog;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddFriendPresenter extends BasePresenterImpl<AddFriendContract.View> implements AddFriendContract.Presenter {
    private String TAG = "AddFriendPresenter";

    @Override
    public void sendAddFriend(Map<String, String> map) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "发送");
        HttpManager.getHttpManager().postMethod(UrlUtils.APPLY_FRIEND, new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);

                if ("0000".equals(codeBean.getCode())) {
                    if (mView != null)
                        mView.sendSucceed();
                } else {
                    if (mView != null)
                        mView.sendFail();
                }
            }
        }, map);
    }

    @Override
    public void yesAgree(String myusername, String fusername, final int position) {
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
                        mView.yesAgreeSuccess(position);
                } else {
                    if (mView != null)
                        mView.yesAgreeError();
                    LogUtils.i(TAG,"code:"+codeBean.getCode());
                }
            }
        }, map);
    }
}
