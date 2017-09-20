package com.ygst.cenggeche.utils;

/**
 * Created by Administrator on 2017/4/10.
 */

public class UrlUtils {
    //BaseUrl本地
//    public static String BASEURl_BENDI = "http://192.168.0.133";
    public static String BASEURl_BENDI = "http://gqj19940824.vicp.io/";

    // BaseUrl测试(暂时没有)
//     public static String BASEURl_TEST = "http://192.168.0.147:8180";
    //BaseUrl线上
    public static String BASEURL_XIANSHANG = "https://www.1yongche.com/apiJ/";
//    public static String BASEURL_XIANSHANG = "https://47.93.6.225/apiJ/";
//    public static String BASEURL_XIANSHANG = "http://47.93.6.225:8180/";

    //服务端地址
    public static String BASEURl = BASEURL_XIANSHANG;

    //H5链接（本地）
//    public static String URL_H5="http://r16878y847.iok.la/car_mm";
    //H5链接（测试）
//    public static String URL_H5="http://192.168.1.148";
    //H5链接（线上）
    public static String URL_H5="http://www.1yongche.com";

    //MD5加密Key
    public static String KEY = "A4G6GH71B28D205SFD7655H785ABE1F6";

    //校验用户帐号是否已被注册接口
    public static String CHECK_IS_REGIST = "user/checkIsRegist.do";
    //获取验证码
    public static String GET_SMS_CODE = "sms/getSMSCode.do ";
    //验证短信验证码是否正确或超时接口
    public static String CHECK_SMS_CODE = "user/checkSmsCode.do";
    //注册接口
    public static String REGIST = "user/regist.do";
    //登录接口
    public static String LOGIN = "user/login.do";
    //重置密码
    public static String RESET_PWD = "user/resetPass.do";
    //退出接口
    public static String LOGIN_OUT = "user/logout.do";

    //获取系统通知接口
    public static String GET_SYSTEM_NOTIFY_HEAD= "inform/systemInformHead.do";
    //获取系统通知列表接口
    public static String GET_SYSTEM_NOTIFY_LIST= "inform/informList.do";

    //获取活动公告接口
    public static String GET_NOTICE_HEAD = "notice/noticeHead.do";
    //获取活动公告列表接口
    public static String GET_NOTICE_LIST = "notice/noticeList.do";
    //获取活动公告详情接口
    public static String GET_NOTICE_INFO = "notice/noticeInfo.do";

    //获取我的信息接口
    public static String GET_MY_INFO = "user/getMyInfo.do";
    //获取用户信息接口
    public static String GET_USER_INFO = "user/getUserInfo.do";
    //获取好友信息接口
    public static String GET_FRIEND_INFO = "user/getFriendsInfo.do";
    //重置密码接口
    public static String RESET_PASS = "user/resetPass.do";
    //申请好友
    public static String APPLY_FRIEND = "friend/applyFriend.do";
    //验证消息列表
    public static String APPLY_LIST="friend/applyList.do";
    //删除验证消息
    public static String APPLY_DELETE_DATE="friend/realDelapplyFriend.do";
    //拒绝对方申请
    public static String APPLY_DATE_NO_AGREE="friend/delapplyFriend.do";
    //同意对方申请
    public static String APPLY_DATE_YES_AGREE="friend/addFriend.do";
    //好友列表
    public static String FRIEND_LIST="friendOperate/friendList.do";
    //黑名单列表
    public static String BLACK_LIST="friendOperate/friendBlackList.do";
    //删除好友
    public static String DELETE_FRIEND="friendOperate/delFriend.do";
    //将好友加入黑名单
    public static String ADD_BLACKLIST="friendOperate/addBlackList.do";
    //移除黑名单
    public static String REMOVE_BLACKLIST="friendOperate/removeBlackList.do";
    //修改好友备注
    public static String UPDATE_FRIEND_NOTE="friendOperate/updateFriendNote.do";
    //获取最新的app版本接口
    public static String GET_NEW_APP_VERSION="version/getNewAppVersion.do";

    // 刷新token
    public static String REFRESHTOKEN = "login/refreshToken.do";


    //获取全部行程（蹭车/捎人）
    public static String ALLTRAVEL = "stroke/getAllStroke.do";

    //发布行程（车主/用户）接口
    public static String RELEASESTROKE = "stroke/releaseStroke.do";

    //确认行程（车主/用户）接口
    public static String CONFIRMSTROKE = "stroke/confirmStroke.do";

    //获取车辆品牌列表接口
    public static String GETALLCARBRAND = "car/getAllCarBrand.do";

    //根据车辆品牌获取车系接口
    public static String GETALLCARTYPEBRAND = "car/getCarTypeByBrand.do";

    //获取附近的人
    public static String GETNEARBYPERSON = "lookAround/list.do";

    //获取城市列表
    public static String GETCITYNAME= "map/getAllCity.do";

    //获取用户当前进行中的行程接口
    public static String GETCURRENTSTROKE= "stroke/getUserCurrentStroke.do";

    //版本升级
    public static String UPDATE= "version/getNewAppVersion.do";

    //检测用户是否可发布行程接口
    public static String CHECKUSERSTATUS= "stroke/checkUserStrokeStatus.do";

    //检测用户是否可申请同行接口
    public static String CHECKUSERAPPLYSTATUS= "stroke/checkUserApplyPeerStatus.do";


    //检测用户资料图片
    public static String GETINFOPIC= "user/getUserPhoto.do";

    //上传用户行程的背景图接口
    public static String UPLOADBG= "upload/uploadStrokeBackPic.do";

    //获取他人行程及用户信息系接口
    public static String GETSTROKEUSERINFO= "stroke/getStrokeAndUserInfo.do";

    //获取他人行程及用户信息系接口
    public static String CHANGEUSERINFO= "stroke/updateStroke.do";


    // 车主/乘客选择行程请求同行接口
    public static String PEERREQUEST= "stroke/peerRequest.do";





}
