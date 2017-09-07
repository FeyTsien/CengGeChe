package com.ygst.cenggeche.bean;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MyInfoBean {
    /**
     * data : {"uid":888,"totalNum":0,"userPic":"http://192.168.1.55/upload/image/user_pic/","nickname":"qing","userStatus":0,"gender":0,"rubNum":0,"passiveRubNum":0}
     * code : 0000
     * msg : 执行成功
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * uid : 888
         * totalNum : 0
         * userPic : http://192.168.1.55/upload/image/user_pic/
         * nickname : qing
         * userStatus : 0
         * gender : 0
         * rubNum : 0
         * passiveRubNum : 0
         */

        private int uid;
        private int totalNum;
        private String userPic;
        private String nickname;
        private int userStatus;
        private int gender;
        private int rubNum;
        private int passiveRubNum;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
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

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getRubNum() {
            return rubNum;
        }

        public void setRubNum(int rubNum) {
            this.rubNum = rubNum;
        }

        public int getPassiveRubNum() {
            return passiveRubNum;
        }

        public void setPassiveRubNum(int passiveRubNum) {
            this.passiveRubNum = passiveRubNum;
        }
    }
}
