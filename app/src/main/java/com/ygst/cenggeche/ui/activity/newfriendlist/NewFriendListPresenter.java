package com.ygst.cenggeche.ui.activity.newfriendlist;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.ApplyBean;
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
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onNext(String s) {
                LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);
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
    public void deleteApplyDate(String myusername, String fusername, final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        map.put("fusername", fusername);
        HttpManager.getHttpManager().postMethod(UrlUtils.APPLY_DELETE_DATE, new Observer<String>() {

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
                        mView.deleteApplyDateSuccess(position);
                } else {
                    if (mView != null)
                        mView.deleteApplyDateError();
                    LogUtils.i(TAG,"code:"+codeBean.getCode());
                }
            }
        }, map);
    }

    @Override
    public void noAgree(String myusername, String fusername) {
        Map<String, String> map = new HashMap<>();
        map.put("myusername", myusername);
        map.put("fusername", fusername);
        HttpManager.getHttpManager().postMethod(UrlUtils.APPLY_DATE_NO_AGREE, new Observer<String>() {
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
                        mView.noAgreeSuccess();
                } else {
                    if (mView != null)
                        mView.noAgreeError();
                    LogUtils.i(TAG,"code:"+codeBean.getCode());
                }
            }
        }, map);
    }

    @Override
    public void yesAgree(String myusername, String fusername) {
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
                        mView.yesAgreeSuccess();
                } else {
                    if (mView != null)
                        mView.yesAgreeError();
                    LogUtils.i(TAG,"code:"+codeBean.getCode());
                }
            }
        }, map);
    }
}
