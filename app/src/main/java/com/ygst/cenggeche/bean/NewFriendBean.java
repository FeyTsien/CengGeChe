package com.ygst.cenggeche.bean;

/**
 * Created by Administrator on 2017/8/29.
 */

public class NewFriendBean {

    private String userName;
    private String reason;
    private String appkey;
    private int unReadCnt;
    private boolean isAgree;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public int getUnReadCnt() {
        return unReadCnt;
    }

    public void setUnReadCnt(int unReadCnt) {
        this.unReadCnt = unReadCnt;
    }

    public boolean isAgree() {
        return isAgree;
    }

    public void setAgree(boolean agree) {
        isAgree = agree;
    }
}
