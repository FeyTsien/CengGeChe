package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class NearByBean {


    /**
     * total : 2
     * data : [{"id":1005,"lit":116.540665,"tags":[],"distance":8596990,"username":"18210568244","userPic":"http://192.168.0.133/image/user_pic/","nickname":"leanper","strokeStatus":"00","age":3,"gender":1,"pic":"","lat":39.766522},{"id":888,"lit":116.540597,"tags":["寂寞的人","2B青年","街拍达人","小仙女","吃货","暖男","老司机","萌汉子"],"distance":8596996,"username":"17301025530","userPic":"http://192.168.0.133/image/user_pic/888_60811506335669654.jpg","nickname":"  ","strokeStatus":"10","age":26,"gender":1,"pic":"http://192.168.0.133/image/user_photo/888/888_44171506334680594.jpg","lat":39.766475}]
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
         * id : 1005
         * lit : 116.540665
         * tags : []
         * distance : 8596990
         * username : 18210568244
         * userPic : http://192.168.0.133/image/user_pic/
         * nickname : leanper
         * strokeStatus : 00
         * age : 3
         * gender : 1
         * pic :
         * lat : 39.766522
         */

        private int id;
        private double lit;
        private int distance;
        private String username;
        private String userPic;
        private String nickname;
        private String strokeStatus;
        private int age;
        private int gender;
        private String pic;
        private double lat;
        private List<String> tags;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLit() {
            return lit;
        }

        public void setLit(double lit) {
            this.lit = lit;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getStrokeStatus() {
            return strokeStatus;
        }

        public void setStrokeStatus(String strokeStatus) {
            this.strokeStatus = strokeStatus;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
