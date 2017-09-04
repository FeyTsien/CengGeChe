package com.ygst.cenggeche.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/29.
 */

public class ApplyBean implements Serializable {

    private String reason;
    private String myUsername;
    private String fromUsername;
    private String fromAvatar;
    private String fromNickname;
    private String fromAppkey;

    //1表示同意，2表示拒绝，3表示没有回复状态
    private int isAgree;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }

    public String getFromAppkey() {
        return fromAppkey;
    }

    public void setFromAppkey(String fromAppkey) {
        this.fromAppkey = fromAppkey;
    }

    public int getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(int isAgree) {
        this.isAgree = isAgree;
    }
}
