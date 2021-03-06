package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class NowTravelInfoBean {


    /**
     * data : {"uid":938,"backgroundPic":"https://image.1yongche.com:8444/background_pic/938/938_27881505480254639.jpg","startAddr":"北京市 通州区 永昌南路30号","strokeType":1,"postedTime":"2017-09-19 04:10:10","userFlag":2,"startCityCode":"","endCityCode":"010","endLoca":"116.480347,39.802959","endAddr":"亦庄桥(地铁站)","ifCity":0,"id":299,"userName":"","plateNo":"","userPic":"","nickname":"哈哈哈","expand3":"","expand2":"","expand1":"","color":"","strokeStatus":20,"brand":"","deparTime":"2017-09-19 23:00","startLoca":"","comments":""}
     * code : 0000
     * msg : 执行成功
     * info : [{"uid":946,"backgroundPic":"https://image.1yongche.com:8444/background_pic/946/https://image.1yongche.com:8444/background_pic/946/946_24141505765332356.jpg","startAddr":"北京市 通州区 永昌南路30号","strokeType":0,"postedTime":"2017-09-19 04:08:53","userFlag":2,"startCityCode":"","endLoca":"116.480347,39.802959","endCityCode":"","endAddr":"亦庄桥(地铁站)","id":298,"ifCity":0,"userName":"MTg5MTA3MDM2NDk=\r\n","plateNo":"","userPic":"https://image.1yongche.com:8444/user_pic/946_61861505475634876.jpg","nickname":"goalboy","expand3":"","expand2":"","expand1":"","color":"棕褐色","strokeStatus":10,"brand":"奥迪 奥迪Q5","deparTime":"2017-09-19 23:00","startLoca":"","comments":""}]
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
         * uid : 938
         * backgroundPic : https://image.1yongche.com:8444/background_pic/938/938_27881505480254639.jpg
         * startAddr : 北京市 通州区 永昌南路30号
         * strokeType : 1
         * postedTime : 2017-09-19 04:10:10
         * userFlag : 2
         * startCityCode :
         * endCityCode : 010
         * endLoca : 116.480347,39.802959
         * endAddr : 亦庄桥(地铁站)
         * ifCity : 0
         * id : 299
         * userName :
         * plateNo :
         * userPic :
         * nickname : 哈哈哈
         * expand3 :
         * expand2 :
         * expand1 :
         * color :
         * strokeStatus : 20
         * brand :
         * deparTime : 2017-09-19 23:00
         * startLoca :
         * comments :
         */

        private int uid;
        private String backgroundPic;
        private String startAddr;
        private int strokeType;
        private String postedTime;
        private int userFlag;
        private String startCityCode;
        private String endCityCode;
        private String endLoca;
        private String endAddr;
        private int ifCity;
        private int id;
        private String userName;
        private String plateNo;
        private String userPic;
        private String nickname;
        private String expand3;
        private String expand2;
        private String expand1;
        private String color;
        private int strokeStatus;
        private String brand;
        private String deparTime;
        private String startLoca;
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

        public String getEndCityCode() {
            return endCityCode;
        }

        public void setEndCityCode(String endCityCode) {
            this.endCityCode = endCityCode;
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

        public int getIfCity() {
            return ifCity;
        }

        public void setIfCity(int ifCity) {
            this.ifCity = ifCity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
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
    }

    public static class InfoBean {
        /**
         * uid : 946
         * backgroundPic : https://image.1yongche.com:8444/background_pic/946/https://image.1yongche.com:8444/background_pic/946/946_24141505765332356.jpg
         * startAddr : 北京市 通州区 永昌南路30号
         * strokeType : 0
         * postedTime : 2017-09-19 04:08:53
         * userFlag : 2
         * startCityCode :
         * endLoca : 116.480347,39.802959
         * endCityCode :
         * endAddr : 亦庄桥(地铁站)
         * id : 298
         * ifCity : 0
         * userName : MTg5MTA3MDM2NDk=

         * plateNo :
         * userPic : https://image.1yongche.com:8444/user_pic/946_61861505475634876.jpg
         * nickname : goalboy
         * expand3 :
         * expand2 :
         * expand1 :
         * color : 棕褐色
         * strokeStatus : 10
         * brand : 奥迪 奥迪Q5
         * deparTime : 2017-09-19 23:00
         * startLoca :
         * comments :
         */

        private int uid;
        private String backgroundPic;
        private String startAddr;
        private int strokeType;
        private String postedTime;
        private int userFlag;
        private String startCityCode;
        private String endLoca;
        private String endCityCode;
        private String endAddr;
        private int id;
        private int ifCity;
        private String userName;
        private String plateNo;
        private String userPic;
        private String nickname;
        private String expand3;
        private String expand2;
        private String expand1;
        private String color;
        private int strokeStatus;
        private String brand;
        private String deparTime;
        private String startLoca;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
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
    }
}
