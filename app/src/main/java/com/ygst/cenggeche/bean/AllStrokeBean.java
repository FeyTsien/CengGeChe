package com.ygst.cenggeche.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AllStrokeBean {

    /**
     * code : 0000
     * data : [{"backgroundPic":"111.jpg","brand":"","color":"","comments":"砍死产品","deparTime":"2017-08-29 10:10","endAddr":"世界的尽头","endLoca":"","expand1":"","expand2":"","expand3":"","id":4,"nickname":"qing","plateNo":"","postedTime":"2017-08-30 17:45:47","startAddr":"嘉捷双子座A座1011","startLoca":"","strokeStatus":10,"uid":888,"userFlag":1,"userPic":""},{"backgroundPic":"","brand":"","color":"","comments":"蹭个车","deparTime":"2017-08-29 11:15","endAddr":"人民广场","endLoca":"76.12143","expand1":"","expand2":"","expand3":"","id":2,"nickname":"huan","plateNo":"","postedTime":"2017-08-29 10:10:55","startAddr":"嘉捷双子座","startLoca":"192.123455","strokeStatus":10,"uid":1,"userFlag":1,"userPic":""}]
     * msg : 执行成功
     * total : 2
     */

    private String code;
    private String msg;
    private int total;
    private ArrayList<DataBean> data;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * backgroundPic : 111.jpg
         * brand :
         * color :
         * comments : 砍死产品
         * deparTime : 2017-08-29 10:10
         * endAddr : 世界的尽头
         * endLoca :
         * expand1 :
         * expand2 :
         * expand3 :
         * id : 4
         * nickname : qing
         * plateNo :
         * postedTime : 2017-08-30 17:45:47
         * startAddr : 嘉捷双子座A座1011
         * startLoca :
         * strokeStatus : 10
         * uid : 888
         * userFlag : 1
         * userPic :
         */

        private String backgroundPic;
        private String brand;
        private String color;
        private String comments;
        private String deparTime;
        private String endAddr;
        private String endLoca;
        private String expand1;
        private String expand2;
        private String expand3;
        private int id;
        private String nickname;
        private String plateNo;
        private String postedTime;
        private String startAddr;
        private String startLoca;
        private int strokeStatus;
        private int uid;
        private int userFlag;
        private String userPic;

        public String getBackgroundPic() {
            return backgroundPic;
        }

        public void setBackgroundPic(String backgroundPic) {
            this.backgroundPic = backgroundPic;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getDeparTime() {
            return deparTime;
        }

        public void setDeparTime(String deparTime) {
            this.deparTime = deparTime;
        }

        public String getEndAddr() {
            return endAddr;
        }

        public void setEndAddr(String endAddr) {
            this.endAddr = endAddr;
        }

        public String getEndLoca() {
            return endLoca;
        }

        public void setEndLoca(String endLoca) {
            this.endLoca = endLoca;
        }

        public String getExpand1() {
            return expand1;
        }

        public void setExpand1(String expand1) {
            this.expand1 = expand1;
        }

        public String getExpand2() {
            return expand2;
        }

        public void setExpand2(String expand2) {
            this.expand2 = expand2;
        }

        public String getExpand3() {
            return expand3;
        }

        public void setExpand3(String expand3) {
            this.expand3 = expand3;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getPostedTime() {
            return postedTime;
        }

        public void setPostedTime(String postedTime) {
            this.postedTime = postedTime;
        }

        public String getStartAddr() {
            return startAddr;
        }

        public void setStartAddr(String startAddr) {
            this.startAddr = startAddr;
        }

        public String getStartLoca() {
            return startLoca;
        }

        public void setStartLoca(String startLoca) {
            this.startLoca = startLoca;
        }

        public int getStrokeStatus() {
            return strokeStatus;
        }

        public void setStrokeStatus(int strokeStatus) {
            this.strokeStatus = strokeStatus;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUserFlag() {
            return userFlag;
        }

        public void setUserFlag(int userFlag) {
            this.userFlag = userFlag;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }
    }
}
