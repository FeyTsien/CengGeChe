package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class CarBrandTypeBean {

    /**
     * data : ["北汽威旺306","北汽威旺307","北汽威旺M20","北汽威旺M30","北汽威旺M35","北汽威旺T205-D","北汽威旺007","北汽威旺205"]
     * code : 0000
     * msg : 执行成功
     */

    private String code;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
