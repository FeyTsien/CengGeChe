package com.ygst.cenggeche.bean;

/**
 * Created by Administrator on 2017/9/16.
 */

public class CheckPeerBean {

    /**
     * data : {"uid":888,"backgroundPic":"8.jpg","userPic":"","startAddr":"嘉捷双子座","strokeType":0,"nickname":"huan","expand3":"","expand2":"","expand1":"","postedTime":"2017-09-08 10:10:55","userFlag":1,"startCityCode":"","endLoca":"76.12143","endCityCode":"","endAddr":"人民广场","id":2,"ifCity":0,"color":"","strokeStatus":10,"brand":"","deparTime":"2017-09-08 11:15","startLoca":"192.123455","comments":"蹭个车","plateNo":""}
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
         * backgroundPic : 8.jpg
         * userPic :
         * startAddr : 嘉捷双子座
         * strokeType : 0
         * nickname : huan
         * expand3 :
         * expand2 :
         * expand1 :
         * postedTime : 2017-09-08 10:10:55
         * userFlag : 1
         * startCityCode :
         * endLoca : 76.12143
         * endCityCode :
         * endAddr : 人民广场
         * id : 2
         * ifCity : 0
         * color :
         * strokeStatus : 10
         * brand :
         * deparTime : 2017-09-08 11:15
         * startLoca : 192.123455
         * comments : 蹭个车
         * plateNo :
         */

        private int uid;
        private String backgroundPic;
        private String userPic;
        private String startAddr;
        private int strokeType;
        private String nickname;
        private String expand3;
        private String expand2;
        private String expand1;
        private String postedTime;
        private int userFlag;
        private String startCityCode;
        private String endLoca;
        private String endCityCode;
        private String endAddr;
        private int id;
        private int ifCity;
        private String color;
        private int strokeStatus;
        private String brand;
        private String deparTime;
        private String startLoca;
        private String comments;
        private String plateNo;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getBackgroundPic() {
            return backgroundPic;
        }

        public void setBackgroundPic(String backgroundPic) {
            this.backgroundPic = backgroundPic;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getStartAddr() {
            return startAddr;
        }

        public void setStartAddr(String startAddr) {
            this.startAddr = startAddr;
        }

        public int getStrokeType() {
            return strokeType;
        }

        public void setStrokeType(int strokeType) {
            this.strokeType = strokeType;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getExpand3() {
            return expand3;
        }

        public void setExpand3(String expand3) {
            this.expand3 = expand3;
        }

        public String getExpand2() {
            return expand2;
        }

        public void setExpand2(String expand2) {
            this.expand2 = expand2;
        }

        public String getExpand1() {
            return expand1;
        }

        public void setExpand1(String expand1) {
            this.expand1 = expand1;
        }

        public String getPostedTime() {
            return postedTime;
        }

        public void setPostedTime(String postedTime) {
            this.postedTime = postedTime;
        }

        public int getUserFlag() {
            return userFlag;
        }

        public void setUserFlag(int userFlag) {
            this.userFlag = userFlag;
        }

        public String getStartCityCode() {
            return startCityCode;
        }

        public void setStartCityCode(String startCityCode) {
            this.startCityCode = startCityCode;
        }

        public String getEndLoca() {
            return endLoca;
        }

        public void setEndLoca(String endLoca) {
            this.endLoca = endLoca;
        }

        public String getEndCityCode() {
            return endCityCode;
        }

        public void setEndCityCode(String endCityCode) {
            this.endCityCode = endCityCode;
        }

        public String getEndAddr() {
            return endAddr;
        }

        public void setEndAddr(String endAddr) {
            this.endAddr = endAddr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIfCity() {
            return ifCity;
        }

        public void setIfCity(int ifCity) {
            this.ifCity = ifCity;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getStrokeStatus() {
            return strokeStatus;
        }

        public void setStrokeStatus(int strokeStatus) {
            this.strokeStatus = strokeStatus;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getDeparTime() {
            return deparTime;
        }

        public void setDeparTime(String deparTime) {
            this.deparTime = deparTime;
        }

        public String getStartLoca() {
            return startLoca;
        }

        public void setStartLoca(String startLoca) {
            this.startLoca = startLoca;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }
    }

    @Override
    public String toString() {
        return "CheckPeerBean{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
