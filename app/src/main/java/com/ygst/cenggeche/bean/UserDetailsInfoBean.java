package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class UserDetailsInfoBean {

    /**
     * data : {"uid":930,"backgroundPic":"https://image.1yongche.com:8444/user_photo/931/930_48741505378404455.jpg","userPic":"","startAddr":"亦庄创意生活广场","strokeType":0,"nickname":"張曼","expand3":"","expand2":"","expand1":"","postedTime":"2017-09-14 19:00:57","userFlag":2,"startCityCode":"010","endLoca":"118.820503,31.943333","endCityCode":"025","endAddr":"北宫门(地铁站)","id":44,"ifCity":1,"color":"绿色","strokeStatus":10,"brand":"一汽夏利","deparTime":"2017-09-14 14:0","startLoca":"116.476822,39.798210","comments":"","plateNo":""}
     * pic : ["https://image.1yongche.com:8444/user_photo/930/930_06701505300810544.jpg","https://image.1yongche.com:8444/user_photo/930/930_87091505300830384.jpg","https://image.1yongche.com:8444/user_photo/930/930_64461505300853607.jpg","https://image.1yongche.com:8444/user_photo/930/930_00041505355282094.jpg"]
     * code : 0000
     * user : {"birthday":"1995-10-23","home":"北京 北京市 房山区","loginTime":"2017-09-14 18:54:03","jingWeiDu":"","registrationId":"1104a8979293de02a0e","loginStatus":1,"location":"北京 北京市 昌平区","tag":["90后"],"rubNum":0,"passiveRubNum":0,"education":40,"userSign":"爱生活，爱自然","password":"","redisTime":"2017-09-12 17:48:58","id":930,"username":"17701314832","distance":"","age":21,"gender":0,"isTestUser":0,"userlev":0,"deviceId":"EDD8FA18AE42ED7163A06B003C185ABE","lat":1,"lit":1,"platform":"android","userPic":"https://image.1yongche.com:8444/user_pic/930_01841505301812497.jpg","nickname":"張曼、","expand3":"","expand2":"","expand1":"","totalNum":0,"useremail":"","userStatus":1,"logoutTime":"2017-09-14 18:53:20","realname":""}
     * msg : 执行成功
     */

    private DataBean data;
    private String code;
    private UserBean user;
    private String msg;
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public static class DataBean {
        /**
         * uid : 930
         * backgroundPic : https://image.1yongche.com:8444/user_photo/931/930_48741505378404455.jpg
         * userPic :
         * startAddr : 亦庄创意生活广场
         * strokeType : 0
         * nickname : 張曼
         * expand3 :
         * expand2 :
         * expand1 :
         * postedTime : 2017-09-14 19:00:57
         * userFlag : 2
         * startCityCode : 010
         * endLoca : 118.820503,31.943333
         * endCityCode : 025
         * endAddr : 北宫门(地铁站)
         * id : 44
         * ifCity : 1
         * color : 绿色
         * strokeStatus : 10
         * brand : 一汽夏利
         * deparTime : 2017-09-14 14:0
         * startLoca : 116.476822,39.798210
         * comments :
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

    public static class UserBean {
        /**
         * birthday : 1995-10-23
         * home : 北京 北京市 房山区
         * loginTime : 2017-09-14 18:54:03
         * jingWeiDu :
         * registrationId : 1104a8979293de02a0e
         * loginStatus : 1
         * location : 北京 北京市 昌平区
         * tag : ["90后"]
         * rubNum : 0
         * passiveRubNum : 0
         * education : 40
         * userSign : 爱生活，爱自然
         * password :
         * redisTime : 2017-09-12 17:48:58
         * id : 930
         * username : 17701314832
         * distance :
         * age : 21
         * gender : 0
         * isTestUser : 0
         * userlev : 0
         * deviceId : EDD8FA18AE42ED7163A06B003C185ABE
         * lat : 1
         * lit : 1
         * platform : android
         * userPic : https://image.1yongche.com:8444/user_pic/930_01841505301812497.jpg
         * nickname : 張曼、
         * expand3 :
         * expand2 :
         * expand1 :
         * totalNum : 0
         * useremail :
         * userStatus : 1
         * logoutTime : 2017-09-14 18:53:20
         * realname :
         */

        private String birthday;
        private String home;
        private String loginTime;
        private String jingWeiDu;
        private String registrationId;
        private int loginStatus;
        private String location;
        private int rubNum;
        private int passiveRubNum;
        private int education;
        private String userSign;
        private String password;
        private String redisTime;
        private int id;
        private String username;
        private String distance;
        private int age;
        private int gender;
        private int isTestUser;
        private int userlev;
        private String deviceId;
        private double lat;
        private double lit;
        private String platform;
        private String userPic;
        private String nickname;
        private String expand3;
        private String expand2;
        private String expand1;
        private int totalNum;
        private String useremail;
        private int userStatus;
        private String logoutTime;
        private String realname;
        private List<String> tag;

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

        public int getLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(int loginStatus) {
            this.loginStatus = loginStatus;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getUserSign() {
            return userSign;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public double getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public double getLit() {
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

        public String getLogoutTime() {
            return logoutTime;
        }

        public void setLogoutTime(String logoutTime) {
            this.logoutTime = logoutTime;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public List<String> getTag() {
            return tag;
        }

        public void setTag(List<String> tag) {
            this.tag = tag;
        }
    }
}
