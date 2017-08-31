package com.ygst.cenggeche.interfaces;

import android.util.Log;

import com.google.gson.Gson;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.bean.CodeMsg;
import com.ygst.cenggeche.bean.RefreshTokenBean;
import com.ygst.cenggeche.utils.RetrofitUtil;
import com.ygst.cenggeche.utils.UrlUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;


/**
 * Created by Administrator on 2017/5/22.
 */

public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        // LogUtil.print("response.code=" + response.code());
        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
            // LogUtil.print("静默自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newSession = getNewToken();
            //使用新的Token，创建新的请求
          /*  FormBody.Builder newFormBody = new FormBody.Builder();


            newFormBody.add("accessToken", newSession);
            newFormBody.add("userId", "105");*/


            if (request.body() instanceof FormBody) {
                FormBody.Builder newFormBody = new FormBody.Builder();
                FormBody oidFormBody = (FormBody) request.body();
                for (int i = 0; i < oidFormBody.size(); i++) {
                    if (oidFormBody.encodedName(i).equals("accessToken"))
                        continue;
                    newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                }
                newFormBody.add("accessToken", newSession);
                Log.i("TokenInterceptor", "intercept: ++++++++++++++++++++++==");
                Request newRequest = chain.request()
                        .newBuilder()
                        .method(request.method(), newFormBody.build())
                        // .header("accessToken", newSession)
                        .build();
                //重新请求
                return chain.proceed(newRequest);
            }
        }
        return response;
    }


    public String getBodyString(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            contentType.charset(charset);
        }
        return buffer.clone().readString(charset);
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        try {
            Gson gson = new Gson();
            CodeMsg codemsg = gson.fromJson(getBodyString(response), CodeMsg.class);
            if ("0005".equals(codemsg.getCode()) || "0006".equals(codemsg.getCode())) {
                MyApplication.clearLogin();
                Log.i("TokenInterceptor", "isTokenExpired: +++++++++++++++++++++++++++++++0005");
                return false;
            } else if (codemsg.getCode().equals("0015")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        Log.i("TAG", "getNewToken: +++++++++++++++");
        Map map = new HashMap();
        map.put("uid", AppData.getUid());
        if (AppData.getAndroidId() != null) {
            map.put("deviceId", AppData.getAndroidId());
        }
        map.put("os", "android");
        Call<String> call = RetrofitUtil.getInstance().get(ProjectAPI.class).refrshToken(UrlUtils.REFRESHTOKEN, map);
        Gson gson = new Gson();
        //  re = gson.fromJson(, RefreshTokenBean.class);
        String re = call.execute().body();
        RefreshTokenBean rrr = gson.fromJson(re, RefreshTokenBean.class);
        AppData.saveToken(rrr.getAccessToken());
        Log.i("TAG", "getNewToken:++++++++++++ " + rrr.getAccessToken());
        return rrr.getAccessToken();
    }
}
