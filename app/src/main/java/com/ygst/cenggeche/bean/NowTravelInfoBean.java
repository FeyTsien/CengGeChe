package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class NowTravelInfoBean {

    /**
     * data : {"uid":888,"backgroundPic":"111.jpg","userPic":"","startAddr":"嘉捷双子座A座1011","nickname":"qing","expand3":"","expand2":"","expand1":"","postedTime":"2017-08-30 17:45:47","userFlag":1,"endLoca":"","endAddr":"世界的尽头","id":4,"color":"","strokeStatus":10,"brand":"","deparTime":"2017-08-29 10:10","startLoca":"","comments":"砍死产品","plateNo":""}
     * code : 0000
     * msg : 执行成功
     * info : [{"uid":889,"backgroundPic":"","userPic":"http://192.168.1.55/upload/image/user_pic/null","startAddr":"","nickname":"qing","expand3":"","expand2":"","expand1":"","postedTime":"","userFlag":0,"endLoca":"","endAddr":"","id":1,"color":"","strokeStatus":0,"brand":"","startLoca":"","deparTime":"","plateNo":"","comments":"蹭个车"}]
     */

    private DataBean data;
    private String code;
    private String msg;
    private List<InfoBean> info;

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

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class DataBean {
        /**
         * uid : 888
         * backgroundPic : 111.jpg
         * userPic :
         * startAddr : 嘉捷双子座A座1011
         * nickname : qing
         * expand3 :
         * expand2 :
         * expand1 :
         * postedTime : 2017-08-30 17:45:47
         * userFlag : 1
         * endLoca :
         * endAddr : 世界的尽头
         * id : 4
         * color :
         * strokeStatus : 10
         * brand :
         * deparTime : 2017-08-29 10:10
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

    public static class InfoBean {
        /**
         * uid : 889
         * backgroundPic :
         * userPic : http://192.168.1.55/upload/image/user_pic/null
         * startAddr :
         * nickname : qing
         * expand3 :
         * expand2 :
         * expand1 :
         * postedTime :
         * userFlag : 0
         * endLoca :
         * endAddr :
         * id : 1
         * color :
         * strokeStatus : 0
         * brand :
         * startLoca :
         * deparTime :
         * plateNo :
         * comments : 蹭个车
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
        private String startLoca;
        private String deparTime;
        private String plateNo;
        private String comments;

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

        public String getStartLoca() {
            return startLoca;
        }

        public void setStartLoca(String startLoca) {
            this.startLoca = startLoca;
        }

        public String getDeparTime() {
            return deparTime;
        }

        public void setDeparTime(String deparTime) {
            this.deparTime = deparTime;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }
}
