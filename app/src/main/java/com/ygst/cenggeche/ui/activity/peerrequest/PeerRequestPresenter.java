package com.ygst.cenggeche.ui.activity.peerrequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.BasePresenterImpl;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UploagImgUrils;
import com.ygst.cenggeche.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PeerRequestPresenter extends BasePresenterImpl<PeerRequestContract.View> implements PeerRequestContract.Presenter{

    private String TAG=this.getClass().getSimpleName();


    @Override
    public void getSureRelease(String userFlag,String usid,String userid, String sid,String startAddr, String endAddr, String startTime, String brand, String color, String strokeFlag,String comments, String filepath) {
        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(mView.getContext(), "正在发布...");
        Map<String, String> map = new HashMap<>();
        map.put("userFlag", userFlag);
        map.put("usid", usid);
        map.put("userId", userid);
        map.put("sid", sid);
        map.put("startAddr", startAddr);
        map.put("endAddr", endAddr);
        map.put("startTime", startTime);
        map.put("brand", brand);
        map.put("color", color);
        map.put("strokeFlag", strokeFlag);
        map.put("showPic", filepath);
        map.put("comments", comments);


        HttpManager.getHttpManager().postMethod(UrlUtils.PEERREQUEST, new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtils.i("HttpManager", "onCompleted:");
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                ToastUtil.show(mView.getContext(), "请求失败，请重试");
                LogUtils.e(TAG, "返回的onError", e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.i("HttpManager", "ssss:" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    if (code.equals("0000")) {
                        mView.sureReleaseSuccess();
                    } else {
                        mView.sureReleaseFail(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }, map);
    }

    //上传图片
    @Override
    public void uploadImg(String pic,Context context) {
        Bitmap bitmap = UploagImgUrils.getBitmap(pic);
        Bitmap bitmap1 = UploagImgUrils.imageZoom(bitmap, 300);
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap1, null, null));
        final String file = UploagImgUrils.getRealFilePath(context, uri);
        // 上传身份证
        Map<String, String> map = new HashMap<>();

        map.put("","");

        HttpManager.getHttpManager().upLoadIcon(UrlUtils.UPLOADBG, file, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Log.i("TAGS", "onError:============== " + e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    JSONObject jsonObject=new JSONObject(string);
                    if(jsonObject.getString("code").equals("0000")){
                        String picUrl = jsonObject.getString("picUrl");
                        mView.uploadImgSuccess(picUrl);
                        Log.i("TAGS", picUrl+"onNext:=====ssss========= ");

                    }else{
                        mView.uoloadFail();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
