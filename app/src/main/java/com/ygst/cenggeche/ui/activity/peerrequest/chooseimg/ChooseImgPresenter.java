package com.ygst.cenggeche.ui.activity.peerrequest.chooseimg;

import android.content.Context;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.bean.ChoosePicBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChooseImgPresenter extends BasePresenterImpl<ChooseImgContract.View> implements ChooseImgContract.Presenter{
    private String TAG=this.getClass().getSimpleName();

    @Override
    public void getUserPic() {
        Map<String, String> map = new HashMap<>();
        map.put("","");
        HttpManager.getHttpManager().postMethod(UrlUtils.GETINFOPIC, new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.show(mView.getContext(), "请求失败，请重试");
                LogUtils.e(TAG, "返回的onError", e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.i("HttpManager", "ssss:" + s);
                //{"pic":["http://192.168.0.133/image/user_photo/922/8.jpg"],"code":"0000","msg":"执行成功"}
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if(code.equals("0000")){
                        Gson gson=new Gson();
                        ChoosePicBean choosePicBean = gson.fromJson(s, ChoosePicBean.class);
                        mView.getUserPicSuccess(choosePicBean);
                    }else{
                        mView.getUserPicFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, map);
    }
}
