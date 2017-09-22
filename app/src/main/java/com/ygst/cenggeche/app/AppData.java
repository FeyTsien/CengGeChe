package com.ygst.cenggeche.app;

import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AppData {

    private static final String IS_NEW_APP = "isNewApp";
    private static final String IS_LOGIN_ED = "isLoginEd";
    private static final String IS_NOTIFICATION = "isNotification";
    private static final String IS_LOCATION = "北京";


    /**
     * 引导页专用
     * @param firstOpen
     */
    public static void savaFirstOpen(int firstOpen) {
        SharedPreferencesUtils.saveInt("FirstOpen", firstOpen);
    }

    /**
     * 查看是否是第一次进入主界面（是否显示引导页）
     */
    public static int getFirstOpen(){
        return SharedPreferencesUtils.getInt("FirstOpen", 1);
    }

    /**
     * 查询是否是最新版APP
     *
     * @return
     */
    public static boolean isNewApp() {
        return SharedPreferencesUtils.getBoolean(IS_NEW_APP, false);
    }

    /**
     * 设置是否是最新版APP
     *
     * @param isNewApp
     */
    public static void setIsNewApp(boolean isNewApp) {
        SharedPreferencesUtils.saveBoolean(IS_NEW_APP, isNewApp);
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
        SharedPreferencesUtils.saveBoolean(IS_LOGIN_ED, isLoginEd);
    }

    /**
     * 设置车主认证状态
     *
     * @param userStatus
     */
    public static void savaUserStatus(int userStatus) {
        SharedPreferencesUtils.saveInt("userStatus", userStatus);
    }

    /**
     * 查询是否车主认证通过
     */
    public static int getUserStatus(){
        return SharedPreferencesUtils.getInt("userStatus", 0);
    }

    /**
     * 保存纬度
     * @param lit
     */
    public static void savaLit(String lit) {
        SharedPreferencesUtils.saveString("lit", lit);
    }

    /**
     * 获取纬度
     */
    public static String getLit(){
        return SharedPreferencesUtils.getString("lit","");
    }
    /**
     * 保存经度
     * @param lat
     */
    public static void savaLat(String lat) {
        SharedPreferencesUtils.saveString("lat", lat);
    }

    /**
     * 获取经度
     */
    public static String getLat(){
        return SharedPreferencesUtils.getString("lat","");
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
        SharedPreferencesUtils.saveBoolean(IS_NOTIFICATION, isLoginEd);
    }

    /**
     * 保存UserId
     */
    public static void saveUid(String username) {
        SharedPreferencesUtils.saveString("UID", username);
    }

    /**
     * 获取UserId
     */
    public static String getUid() {
        return SharedPreferencesUtils.getString("UID", null);
    }

    /**
     * 保存UserName
     */
    public static void saveUserName(String username) {
        SharedPreferencesUtils.saveString("USERNAME", username);
    }
    /**
     * 获取UserName
     */
    public static String getUserName() {
        return SharedPreferencesUtils.getString("USERNAME", null);
    }
    /**
     * 保存Nickname
     */
    public static void saveNickname(String username) {
        SharedPreferencesUtils.saveString("NICKNAME", username);
    }
    /**
     * 获取UserName
     */
    public static String getNickname() {
        return SharedPreferencesUtils.getString("NICKNAME", null);
    }

    /**
     * 保存头像
     */
    public static void saveIcon(String iconPath) {
        SharedPreferencesUtils.saveString("ICON", iconPath);
    }
    /**
     * 获取头像
     */
    public static String getIcon() {
        return SharedPreferencesUtils.getString("ICON", null);
    }

    /**
     * 保存手机设备ID
     */
    public static void saveAndroidId() {
        SharedPreferencesUtils.saveString("ANDROID_ID", CommonUtils.getIMEI(MyApplication.getContext()));
    }

    /**
     * 获取手机设备ID
     */
    public static String getAndroidId() {
        return SharedPreferencesUtils.getString("ANDROID_ID", null);
    }

    /**
     * 保存RegistrationId
     */
    public static void saveRegistrationId(String userId) {
        SharedPreferencesUtils.saveString("REGISTRATION_ID", userId);
    }

    /**
     * 获取RegistrationId
     */
    public static String getRegistrationId() {
        return SharedPreferencesUtils.getString("REGISTRATION_ID", "");
    }

    // 保存验证信息未读取条数
    public static void savaUnReadApplyCount(int count) {
        SharedPreferencesUtils.saveInt("UNREADCOUNT", count);
    }
    // 获取验证信息未读取条数
    public static int getUnReadApplyCount(){
        return SharedPreferencesUtils.getInt("UNREADCOUNT", 0);
    }

    /**
     * 保存当前城市
     */
    public static void saveLocation(String location) {
        SharedPreferencesUtils.saveString("LOCATION", location);
    }
    /**
     * 获取当前城市
     */
    public static String getLocation() {
        return SharedPreferencesUtils.getString("LOCATION", null);
    }

    /**
     * 保存性别
     */
    public static void saveGenders(String gender) {
        SharedPreferencesUtils.saveString("GENDER", gender);
    }
    /**
     * 获取性别
     */
    public static String getGenders() {
        return SharedPreferencesUtils.getString("GENDER", null);
    }


}
