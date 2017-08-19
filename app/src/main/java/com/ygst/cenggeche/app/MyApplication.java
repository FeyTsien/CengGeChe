package com.ygst.cenggeche.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.SharedPreferencesUtils;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;


/**
 * @author :   FeyTsien
 * @date :   2017/8/15
 */

public class MyApplication extends Application {
    private static final String IS_LOGIN_ED = "isLoginEd";
    private static final String IS_NOTIFICATION = "isNotification";
    private static final String IS_OWNER = "isOwner";
    private static ArrayList<Activity> activityList = new ArrayList();
    private static Context mContext = null;
    private static boolean isLoginEd = false;
    public static String location = "北京";

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }

    /**
     * 查询是否登陆
     *
     * @return
     */
    public static boolean isLoginEd() {
        return SharedPreferencesUtils.getBoolean(IS_LOGIN_ED, false);
    }

    /**
     * 设置是否登陆
     *
     * @param isLoginEd
     */
    public static void setIsLoginEd(boolean isLoginEd) {
        MyApplication.isLoginEd = isLoginEd;
        SharedPreferencesUtils.saveBoolean(IS_LOGIN_ED, isLoginEd);
    }

    /**
     * 查询是否勾选不再提示（开始通知栏权限时）
     *
     * @return
     */
    public static boolean isNotification() {
        return SharedPreferencesUtils.getBoolean(IS_NOTIFICATION, false);
    }

    /**
     * 设置是否勾选不再提示（开始通知栏权限时）
     *
     * @param isLoginEd
     */
    public static void setIsNotification(boolean isLoginEd) {
        MyApplication.isLoginEd = isLoginEd;
        SharedPreferencesUtils.saveBoolean(IS_NOTIFICATION, isLoginEd);
    }


    /**
     * 保存用户类型
     */
    public static void saveUserStatus(String token) {
        SharedPreferencesUtils.saveString("UserStatus", token);
    }
    /*
    获取 用户类型
     */

    public static String getUserStatus() {
        return SharedPreferencesUtils.getString("UserStatus", null);
    }

    /**
     * 保存token
     */
    public static void saveToken(String token) {
        SharedPreferencesUtils.saveString("TOKEN", token);
    }
    /*
    获取token
     */

    public static String getToken() {
        return SharedPreferencesUtils.getString("TOKEN", null);
    }

    /**
     * 保存头像
     */
    public static void saveIcon(String token) {
        SharedPreferencesUtils.saveString("ICON", token);
    }
    /*
    获取token
     */

    public static String getIcon() {
        return SharedPreferencesUtils.getString("ICON", null);
    }

    /**
     * 保存carID
     */
    public static void saveUpCarId(String token) {
        SharedPreferencesUtils.saveString("CarId", token);
    }
    /*
    获取carID
     */

    public static String getAndroidId() {
        return SharedPreferencesUtils.getString("ANDROID_ID", null);
    }


    /**
     * 保存carID
     */
    public static void saveAndroidId() {
        SharedPreferencesUtils.saveString("ANDROID_ID", CommonUtils.getIMEI(MyApplication.getContext()));
    }
    /*
    获取carID
     */

    public static String getUpCarId() {
        return SharedPreferencesUtils.getString("CarId", null);
    }

    /**
     * 保存UsrId
     */
    public static void saveUserId(String userId) {
        SharedPreferencesUtils.saveString("USER_ID", userId);
    }

    /**
     * 获取UsrId
     */
    public static String getUserId() {
        return SharedPreferencesUtils.getString("USER_ID", "");
    }

    /**
     * 保存Id
     */
    public static void saveId(String id) {
        SharedPreferencesUtils.saveString("SID", id);
    }

    /**
     * 获取Id
     */
    public static String getId() {
        return SharedPreferencesUtils.getString("SID", "");
    }

    /**
     * 保存UsrId
     */
    public static void saveRegistrationId(String userId) {
        SharedPreferencesUtils.saveString("REGISTRATION_ID", userId);
    }

    /**
     * 获取UsrId
     */
    public static String getRegistrationId() {
        return SharedPreferencesUtils.getString("REGISTRATION_ID", "");
    }

    /**
     * 保存商家用户名
     */
    public static void saveStoreOwn(String storeUserName) {
        SharedPreferencesUtils.saveString("STORE_USER_NAME", storeUserName);
    }

    /**
     * 获取商家用户名
     */
    public static String getStoreOwn() {
        return SharedPreferencesUtils.getString("STORE_USER_NAME", "");
    }

    /**
     * 保存商家店名
     */
    public static void saveStore(String storeName) {
        SharedPreferencesUtils.saveString("STORE_NAME", storeName);
    }

    /**
     * 获取商家店名
     */
    public static String getStore() {
        return SharedPreferencesUtils.getString("STORE_NAME", "");
    }

    /**
     * 保存商家地址
     */
    public static void saveStoreAddr(String storeAddr) {
        SharedPreferencesUtils.saveString("STORE_ADDR", storeAddr);
    }

    /**
     * 获取商家地址
     */
    public static String getStoreAddr() {
        return SharedPreferencesUtils.getString("STORE_ADDR", "");
    }

    /**
     * 保存商家电话
     */
    public static void savePhone(String storeAddr) {
        SharedPreferencesUtils.saveString("PHONE", storeAddr);
    }

    /**
     * 获取商家电话
     */
    public static String getPhone() {
        return SharedPreferencesUtils.getString("PHONE", "");
    }


    // 保存验证状态
    public static void savaStatus(String time) {
        SharedPreferencesUtils.saveString("STATTUS", time);
    }

    public static String getStatus() {
        return SharedPreferencesUtils.getString("STATTUS", "0");
    }

    //清除登陆
    public static void clearLogin() {
        Log.i("clearLogin", "isTokenExpired: +++++++++++++++++++++++++++++++0005");
        SharedPreferencesUtils.saveString("USER_ID", "");
        SharedPreferencesUtils.saveBoolean(IS_LOGIN_ED, false);
        setIsLoginEd(false);
        saveStoreOwn("");
        saveStore("");
        saveStoreAddr("");
        savePhone("");

        saveToken("");
        saveUpCarId("");
        saveIcon("");
        savaStatus("");
        saveUserStatus("");

    }

    private static Handler handler;
    private static int mainThreadId;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        System.out.println("获取context：etApplicationContext()");
        mContext = getApplicationContext();

        handler = new Handler();//创建Handle
        mainThreadId = Process.myTid();//得到主线程id
        isLoginEd = SharedPreferencesUtils.getBoolean(IS_LOGIN_ED, false);
        saveAndroidId();
        // autolayout 的适配初始化 包括状态栏和底部操作栏
//        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

        //极光配置
        //您可以在开发状态中启用调试模式。当发布时，您应该关闭调试模式.
        JMessageClient.setDebugMode(true);
        //init 初始化SDK
        JMessageClient.init(mContext);
    }

    /**
     * 解决java.lang.NoClassDefFoundError错误，方法数超过65536了
     * 5.0以下系统会出次问题
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
