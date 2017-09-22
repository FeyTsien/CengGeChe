package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class NearByBean {

    /**
     * total : 3
     * data : [{"id":935,"lit":116.540857,"tags":[],"distance":8596973,"username":"15116920121","userPic":"http://192.168.0.133/image/user_pic/","nickname":"mg","age":0,"gender":0,"pic":"http://192.168.0.133/image/user_photo/935_62381506067998803.jpg","lat":39.76665},{"id":919,"lit":116.540637,"tags":[],"distance":8596992,"username":"18500632163","userPic":"http://192.168.0.133/image/user_pic/888.jpg","nickname":"耿庆佳","age":0,"gender":0,"pic":"http://192.168.0.133/image/user_photo/919_85631506058473705.jpg","lat":39.766549},{"id":888,"lit":116.5406532,"tags":["萌汉子","老司机","暖男","吃货","小仙女","街拍达人","2B青年","寂寞的人","影视达人"],"distance":8596998,"username":"17301025530","userPic":"http://192.168.0.133/image/user_pic/888_86191506050647650.jpg","nickname":"D83DDE19 D83DDE19 D83DDE19","age":26,"gender":1,"pic":"http://192.168.0.133/image/user_photo/888_50711505900192555.jpg","lat":39.765616}]
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
         * id : 935
         * lit : 116.540857
         * tags : []
         * distance : 8596973
         * username : 15116920121
         * userPic : http://192.168.0.133/image/user_pic/
         * nickname : mg
         * age : 0
         * gender : 0
         * pic : http://192.168.0.133/image/user_photo/935_62381506067998803.jpg
         * lat : 39.76665
         */

        private int id;
        private double lit;
        private int distance;
        private String username;
        private String userPic;
        private String nickname;
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
