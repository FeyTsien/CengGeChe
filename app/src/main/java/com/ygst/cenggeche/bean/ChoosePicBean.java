package com.ygst.cenggeche.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ChoosePicBean {

    /**
     * pic : ["http://192.168.0.133/image/user_photo/922/8.jpg"]
     * code : 0000
     * msg : 执行成功
     */

    private String code;
    private String msg;
    private ArrayList<String> pic;

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

    public ArrayList<String> getPic() {
        return pic;
    }

    public void setPic(ArrayList<String> pic) {
        this.pic = pic;
    }
}
