package com.ygst.cenggeche.manager;


import android.text.TextUtils;

import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.interfaces.ProjectAPI;
import com.ygst.cenggeche.utils.RSAUtil;
import com.ygst.cenggeche.utils.RetrofitUtil;
import com.ygst.cenggeche.utils.SignUtils;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * w网络请求的工具类
 */

public class HttpManager {

    private String TAG = "HttpManager";

    public static HttpManager httpManager = new HttpManager();

    private HttpManager() {

    }


    public static HttpManager getHttpManager() {
        return httpManager;
    }


    /**
     * get方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void getMethod(String url, Observer<String> observer) {
        //获取被观察者
        Observable<String> observable = RetrofitUtil.getInstance().get2(ProjectAPI.class).getMethod(url);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    /**
     * Post方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void postMethod(String url, Observer<String> observer, Map map) {
        String deviceId = "";
        String uid = "";
        if (AppData.getAndroidId() != null) {
            deviceId = AppData.getAndroidId();
        }
        if (AppData.getUid() != null) {
            uid = AppData.getUid();
        }
        String singStr = SignUtils.payParamsToString(map, false);
        String sign = RSAUtil.encryptByPublic(MyApplication.getContext(),singStr);
        Observable<String> observable = RetrofitUtil.getInstance().get(ProjectAPI.class).postMethod(deviceId, uid, sign, url, map);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    /**
     * Post方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void postMethod2(String url, Observer<String> observer, Map map) {
//        if (AppData.getToken() != null)
//            map.put("accessToken", AppData.getToken());
        if (AppData.getUid() != null)
            map.put("userId", AppData.getUid());
        if (AppData.getAndroidId() != null) {
            map.put("deviceId", AppData.getAndroidId());
        }
        map.put("os", "android");
        Observable<String> observable = RetrofitUtil.getInstance().get(ProjectAPI.class).postMethod(url, map);


        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).

                observeOn(AndroidSchedulers.mainThread()).

                subscribe(observer);

    }

    /**
     * 上传单张图片
     */
    public void upLoadIcon(String url, String path, Map map, Observer<ResponseBody> observer) {
        String deviceId = "";
        String uid = "";
        if (AppData.getAndroidId() != null) {
            deviceId = AppData.getAndroidId();
        }
        if (AppData.getUid() != null) {
            uid = AppData.getUid();
        }
        String singStr = SignUtils.payParamsToString(map, false);
        String sign = RSAUtil.encryptByPublic(MyApplication.getContext(),singStr);

        File file = new File(path);
        RequestBody requestbody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("pic", file.getName(), requestbody);
        Observable<ResponseBody> responseBodyObservable;
        if (map != null) {
            responseBodyObservable = RetrofitUtil.getInstance().get(ProjectAPI.class).upLoadImg(deviceId, uid,sign,url, body, map);
        } else {
            responseBodyObservable = RetrofitUtil.getInstance().get(ProjectAPI.class).upLoadImg(deviceId, uid,sign,url, body);
        }
        responseBodyObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }



    /**
     * 上传多张图片以及文件
     */

    public void upLoadImgs(String url, String[] file, Observer<ResponseBody> observer, Map map) {
//        RequestBody assboy = RequestBody.create(MediaType.parse("text/plain"), AppData.getToken());
//        RequestBody userboy = RequestBody.create(MediaType.parse("text/plain"), AppData.getUserId());
//        RequestBody dvice_id = RequestBody.create(MediaType.parse("text/plain"), MyApplication.getAndroidId());
//        RequestBody os = RequestBody.create(MediaType.parse("text/plain"), "android");
//        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), MyApplication.getUpCarId());
        String deviceId = "";
        String uid = "";
        if (AppData.getAndroidId() != null) {
            deviceId = AppData.getAndroidId();
        }
        if (AppData.getUid() != null) {
            uid = AppData.getUid();
        }
        String singStr = SignUtils.payParamsToString(map, false);
        String sign = RSAUtil.encryptByPublic(MyApplication.getContext(),singStr);

        MultipartBody.Part pic1body = null;
        if (!TextUtils.isEmpty(file[0])) {
            RequestBody requestbody = RequestBody.create(MediaType.parse("image/jpg"), new File(file[0]));
            pic1body = MultipartBody.Part.createFormData("pic1", file[0], requestbody);
        }
        MultipartBody.Part pic2body = null;
        if (!TextUtils.isEmpty(file[1])) {
            RequestBody requestbody2 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[1]));
            pic2body = MultipartBody.Part.createFormData("pic2", file[1], requestbody2);
        }
        MultipartBody.Part pic3body = null;
        if (!TextUtils.isEmpty(file[2])) {
            RequestBody requestbody3 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[2]));
            pic3body = MultipartBody.Part.createFormData("pic3", file[2], requestbody3);
        }
        MultipartBody.Part pic4body = null;
        if (!TextUtils.isEmpty(file[3])) {
            RequestBody requestbody4 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[3]));
            pic4body = MultipartBody.Part.createFormData("pic4", file[3], requestbody4);
        }
        MultipartBody.Part pic5body = null;
        if (!TextUtils.isEmpty(file[4])) {
            RequestBody requestbody5 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[4]));
            pic5body = MultipartBody.Part.createFormData("pic5", file[4], requestbody5);
        }
        MultipartBody.Part pic6body = null;
        if (!TextUtils.isEmpty(file[5])) {
            RequestBody requestbody6 = RequestBody.create(MediaType.parse("image/jpg"), new File(file[5]));
            pic6body = MultipartBody.Part.createFormData("pic6", file[5], requestbody6);
        }
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().get(ProjectAPI.class).upLoadsImgs(deviceId, uid,sign,url,map, pic1body, pic2body, pic3body, pic4body, pic5body, pic6body);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

}
