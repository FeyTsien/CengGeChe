package com.ygst.cenggeche.ui.activity.setnotename;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetNoteNamePresenter extends BasePresenterImpl<SetNoteNameContract.View> implements SetNoteNameContract.Presenter{

    private String TAG = "SetNoteNamePresenter";
    @Override
    public void updateNoteName(Map map) {
        HttpManager.getHttpManager().postMethod(UrlUtils.UPDATE_FRIEND_NOTE, new Observer<String>() {

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
                        mView.updateNoteNameSuccess();
                } else {
                    if (mView != null)
                        mView.updateNoteNameError();
//                    ToastUtil.show(mView.getContext(), codeBean.getMsg());
                }
            }
        }, map);
    }
}
