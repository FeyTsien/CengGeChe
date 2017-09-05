package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/3.
 */

public class UserBean {


    /**
     * data : {"birthday":"1994-08-24","home":"","loginTime":"2017-08-28 18:23:31","jingWeiDu":"199,11","registrationId":"18500632163","location":"","tag":"","rubNum":0,"passiveRubNum":0,"education":0,"password":"18500632163","userSign":"","redisTime":"2017-08-28 17:42:44","id":888,"username":"18500632163","distance":"","age":23,"gender":1,"isTestUser":0,"userlev":0,"deviceId":"1111111111111111111111111","lat":11,"lit":199,"platform":"android","userPic":"http://192.168.1.55/upload/image/user_pic/","nickname":"qing","expand3":"","expand2":"","expand1":"","totalNum":0,"useremail":"","userStatus":0,"realname":""}
     * pic : ["http:192.168.0.133/user_pic/888.jpg"]
     * code : 0000
     * msg : 执行成功
     * authStatus : 0
     */

    private DataBean data;
    private String code;
    private String msg;
    private int authStatus;
    private List<String> pic;

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

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public static class DataBean {
        /**
         * birthday : 1994-08-24
         * home :
         * loginTime : 2017-08-28 18:23:31
         * jingWeiDu : 199,11
         * registrationId : 18500632163
         * location :
         * tag :
         * rubNum : 0
         * passiveRubNum : 0
         * education : 0
         * password : 18500632163
         * userSign :
         * redisTime : 2017-08-28 17:42:44
         * id : 888
         * username : 18500632163
         * distance :
         * age : 23
         * gender : 1
         * isTestUser : 0
         * userlev : 0
         * deviceId : 1111111111111111111111111
         * lat : 11
         * lit : 199
         * platform : android
         * userPic : http://192.168.1.55/upload/image/user_pic/
         * nickname : qing
         * expand3 :
         * expand2 :
         * expand1 :
         * totalNum : 0
         * useremail :
         * userStatus : 0
         * realname :
         */

        private String birthday;
        private String home;
        private String loginTime;
        private String jingWeiDu;
        private String registrationId;
        private String location;
        private List<String> tag;
        private int rubNum;
        private int passiveRubNum;
        private int education;
        private String password;
        private String userSign;
        private String redisTime;
        private int id;
        private String username;
        private String distance;
        private int age;
        private int gender;
        private int isTestUser;
        private int userlev;
        private String deviceId;
        private int lat;
        private int lit;
        private String platform;
        private String userPic;
        private String nickname;
        private String expand3;
        private String expand2;
        private String expand1;
        private int totalNum;
        private String useremail;
        private int userStatus;
        private String realname;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getJingWeiDu() {
            return jingWeiDu;
        }

        public void setJingWeiDu(String jingWeiDu) {
            this.jingWeiDu = jingWeiDu;
        }

        public String getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<String> getTag() {
            return tag;
        }

        public void setTag(List<String> tag) {
            this.tag = tag;
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

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserSign() {
            return userSign;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public String getRedisTime() {
            return redisTime;
        }

        public void setRedisTime(String redisTime) {
            this.redisTime = redisTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public int getIsTestUser() {
            return isTestUser;
        }

        public void setIsTestUser(int isTestUser) {
            this.isTestUser = isTestUser;
        }

        public int getUserlev() {
            return userlev;
        }

        public void setUserlev(int userlev) {
            this.userlev = userlev;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public int getLit() {
            return lit;
        }

        public void setLit(int lit) {
            this.lit = lit;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
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

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public String getUseremail() {
            return useremail;
        }

        public void setUseremail(String useremail) {
            this.useremail = useremail;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
