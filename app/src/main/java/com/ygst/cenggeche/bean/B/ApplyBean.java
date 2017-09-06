package com.ygst.cenggeche.bean.B;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class ApplyBean {
    /**
     * data : [{"applyInfo":"sdfa","fusername":"123456","id":4,"userPic":"jkljk","nickname":"ljkl","appkey":"","applyTime":"2017-08-30 14:44:16","myusername":"123","isAgree":3},{"applyInfo":"sdfa","fusername":"123456","id":1,"userPic":"jkljk","nickname":"ljkl","appkey":"","applyTime":"2017-08-30 11:28:51","myusername":"123","isAgree":3}]
     * code : 0000
     * msg : 成功
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
         * applyInfo : sdfa
         * fusername : 123456
         * id : 4
         * userPic : jkljk
         * nickname : ljkl
         * appkey :
         * applyTime : 2017-08-30 14:44:16
         * myusername : 123
         * isAgree : 3
         */

        private String applyInfo;
        private String fusername;
        private int id;
        private String userPic;
        private String nickname;
        private String appkey;
        private String applyTime;
        private String myusername;
        private int isAgree;

        public String getApplyInfo() {
            return applyInfo;
        }

        public void setApplyInfo(String applyInfo) {
            this.applyInfo = applyInfo;
        }

        public String getFusername() {
            return fusername;
        }

        public void setFusername(String fusername) {
            this.fusername = fusername;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getMyusername() {
            return myusername;
        }

        public void setMyusername(String myusername) {
            this.myusername = myusername;
        }

        public int getIsAgree() {
            return isAgree;
        }

        public void setIsAgree(int isAgree) {
            this.isAgree = isAgree;
        }
    }
}
