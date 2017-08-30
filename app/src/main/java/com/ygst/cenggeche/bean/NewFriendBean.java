package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class NewFriendBean {


    /**
     * data : [{"uid":123,"id":1,"fusername":"123456","applyInfo":"sdfa","appkey":"","applyTime":"2017-08-30 11:28:51","isAgree":3},"......"]
     * code : 0000
     * msg : 返回成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 123
         * id : 1
         * fusername : 123456
         * applyInfo : sdfa
         * appkey :
         * applyTime : 2017-08-30 11:28:51
         * isAgree : 3
         */

        private int uid;
        private int id;
        private String fusername;
        private String applyInfo;
        private String appkey;
        private String applyTime;
        private int isAgree;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFusername() {
            return fusername;
        }

        public void setFusername(String fusername) {
            this.fusername = fusername;
        }

        public String getApplyInfo() {
            return applyInfo;
        }

        public void setApplyInfo(String applyInfo) {
            this.applyInfo = applyInfo;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public int getIsAgree() {
            return isAgree;
        }

        public void setIsAgree(int isAgree) {
            this.isAgree = isAgree;
        }
    }
}
