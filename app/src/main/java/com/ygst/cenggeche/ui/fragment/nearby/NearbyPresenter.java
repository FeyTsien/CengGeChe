package com.ygst.cenggeche.ui.fragment.nearby;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.NearByBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NearbyPresenter extends BasePresenterImpl<NearbyContract.View> implements NearbyContract.Presenter{
    private String TAG="NearbyPresenter";

    @Override
    public void getnearBy( String lit, String lat, int page) {
        Map<String, String> map = new HashMap<>();

        map.put("lit",lit);
        map.put("lat",lat);
        map.put("page",page+"");
        HttpManager.getHttpManager().postMethod(UrlUtils.GETNEARBYPERSON, new Observer<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    ToastUtil.show(mView.getContext(), "请求失败，请重试");
                }
                LogUtils.e(TAG, "返回的onError", e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                NearByBean nearByBean = gson.fromJson(s, NearByBean.class);
                String msg = nearByBean.getMsg();
                if ("0000".equals(nearByBean.getCode())) {
                    if (mView != null) {
                        mView.getnearbySuccess(nearByBean);
                    }
                } else {
                    if (mView != null) {
                        mView.getnearbyFail(msg);
                    }
                }
            }
        }, map);
    }
}
