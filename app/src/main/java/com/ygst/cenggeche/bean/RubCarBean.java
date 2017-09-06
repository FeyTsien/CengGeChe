package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class RubCarBean {

    /**
     * total : 2
     * data : [{"uid":888,"backgroundPic":"","userPic":"","startAddr":"","nickname":"qing","expand3":"","expand2":"","expand1":"","postedTime":"","userFlag":0,"endLoca":"","endAddr":"","id":4,"color":"","strokeStatus":0,"brand":"","deparTime":"","startLoca":"","comments":"砍死产品","plateNo":""},{"uid":1,"backgroundPic":"","userPic":"","startAddr":"","nickname":"huan","expand3":"","expand2":"","expand1":"","postedTime":"","userFlag":0,"endLoca":"","endAddr":"","id":2,"color":"","strokeStatus":0,"brand":"","deparTime":"","startLoca":"","comments":"蹭个车","plateNo":""}]
     * code : 0000
     * msg : 执行成功
     */

    private int total;
    private String code;
    private String msg;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 888
         * backgroundPic :
         * userPic :
         * startAddr :
         * nickname : qing
         * expand3 :
         * expand2 :
         * expand1 :
         * postedTime :
         * userFlag : 0
         * endLoca :
         * endAddr :
         * id : 4
         * color :
         * strokeStatus : 0
         * brand :
         * deparTime :
         * startLoca :
         * comments : 砍死产品
         * plateNo :
         */

        private int uid;
        private String backgroundPic;
        private String userPic;
        private String startAddr;
        private String nickname;
        private String expand3;
        private String expand2;
        private String expand1;
        private String postedTime;
        private int userFlag;
        private String endLoca;
        private String endAddr;
        private int id;
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

        public String getEndLoca() {
            return endLoca;
        }

        public void setEndLoca(String endLoca) {
            this.endLoca = endLoca;
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
}
