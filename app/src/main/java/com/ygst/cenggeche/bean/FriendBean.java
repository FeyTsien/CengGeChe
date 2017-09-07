package com.ygst.cenggeche.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class FriendBean implements Serializable {

    /**
     * data : [{"friendTime":"2017-08-30 18:32:48","id":1,"userPic":"","nickname":"","friendusername":"erwsf","operationTime":"2017-08-30 16:19:59","gender":0,"myusername":"456a","friendNote":"sf","friendStatus":1},{"friendTime":"2017-08-30 19:08:02","id":2,"userPic":"","nickname":"","friendusername":"sdfasf","operationTime":"2017-08-31 16:36:04","gender":0,"myusername":"456a","friendNote":"sf","friendStatus":1}]
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
         * friendTime : 2017-08-30 18:32:48
         * id : 1
         * userPic :
         * nickname :
         * friendusername : erwsf
         * operationTime : 2017-08-30 16:19:59
         * gender : 0
         * myusername : 456a
         * friendNote : sf
         * friendStatus : 1
         */

        private String indexTag;
        public String getIndexTag() {
            return indexTag;
        }
        public void setIndexTag(String indexTag) {
            this.indexTag = indexTag;
        }

        private String friendTime;
        private int id;
        private String userPic;
        private String nickname;
        private String friendusername;
        private String operationTime;
        private int gender;
        private String myusername;
        private String friendNote;
        private int friendStatus;
        private int isBlack;

        public String getFriendTime() {
            return friendTime;
        }

        public void setFriendTime(String friendTime) {
            this.friendTime = friendTime;
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

        public String getFriendusername() {
            return friendusername;
        }

        public void setFriendusername(String friendusername) {
            this.friendusername = friendusername;
        }

        public String getOperationTime() {
            return operationTime;
        }

        public void setOperationTime(String operationTime) {
            this.operationTime = operationTime;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMyusername() {
            return myusername;
        }

        public void setMyusername(String myusername) {
            this.myusername = myusername;
        }

        public String getFriendNote() {
            return friendNote;
        }

        public void setFriendNote(String friendNote) {
            this.friendNote = friendNote;
        }

        public int getFriendStatus() {
            return friendStatus;
        }

        public void setFriendStatus(int friendStatus) {
            this.friendStatus = friendStatus;
        }

        public int getIsBlack() {
            return isBlack;
        }

        public void setIsBlack(int isBlack) {
            this.isBlack = isBlack;
        }
    }
}
