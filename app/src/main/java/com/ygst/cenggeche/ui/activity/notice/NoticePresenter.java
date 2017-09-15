package com.ygst.cenggeche.ui.activity.notice;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.notice.NoticeListBean;
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

public class NoticePresenter extends BasePresenterImpl<NoticeContract.View> implements NoticeContract.Presenter{

    private String TAG = "NoticePresenter";
    @Override
    public void getNoticeList() {

        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpManager.getHttpManager().postMethod(UrlUtils.GET_NOTICE_LIST, new Observer<String>() {
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
                NoticeListBean noticeListBean = gson.fromJson(s, NoticeListBean.class);

                if ("0000".equals(noticeListBean.getCode())) {
                    if (mView != null)
                        mView.getNoticeListSuccess(noticeListBean);
                } else {
                    if (mView != null)
                        mView.getNoticeListError();
                }
            }
        }, map);
    }
}
