package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class NearByBean {


    /**
     * code : 0000
     * data : [{"age":0,"distance":"0.00","gender":0,"id":1,"nickname":"Test1","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"17232424342"},{"age":0,"distance":"0.00","gender":0,"id":921,"nickname":"Test","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"17000000000"},{"age":0,"distance":"0.22","gender":0,"id":2,"nickname":"Test2","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"15116132145"},{"age":0,"distance":"0.22","gender":0,"id":922,"nickname":"钱小飞","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"15116920121"},{"age":0,"distance":"0.44","gender":1,"id":923,"nickname":"东东","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"13293158848"},{"age":26,"distance":"0.44","gender":2,"id":66,"nickname":"12345","tags":["2","1","1"],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"15810420717"},{"age":0,"distance":"0.44","gender":1,"id":6,"nickname":"Test6","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"13191977464"},{"age":26,"distance":"0.44","gender":1,"id":5,"nickname":"Test5","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"18733548657"},{"age":26,"distance":"0.44","gender":2,"id":4,"nickname":"Test4","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"15810421464"},{"age":0,"distance":"0.44","gender":1,"id":3,"nickname":"Test3","tags":[],"userPic":"http://192.168.0.133/image/user_pic/888.jpg","username":"13293153221"}]
     * msg : 执行成功
     * total : 11
     */

    private String code;
    private String msg;
    private int total;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * age : 0
         * distance : 0.00
         * gender : 0
         * id : 1
         * nickname : Test1
         * tags : []
         * userPic : http://192.168.0.133/image/user_pic/888.jpg
         * username : 17232424342
         */

        private String age;
        private String distance;
        private int gender;
        private int id;
        private String nickname;
        private String userPic;
        private String username;
        private List<String> tags;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
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

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
