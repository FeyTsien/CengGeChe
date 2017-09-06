package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */

public class CxBean {

    /**
     * data : ["allroad","Crosslane Coupe","e-tron quattro","Nanuk","Prologue","quattro","奥迪100","奥迪A1","奥迪A2","奥迪A3","奥迪A3(进口)","奥迪A4","奥迪A4(进口)","奥迪A4L","奥迪A5","奥迪A6","奥迪A6(进口)","奥迪A6L","奥迪A7","奥迪A8","奥迪Cross","奥迪e-tron","奥迪Q2","奥迪Q3","奥迪Q3(进口)","奥迪Q5","奥迪Q5(进口)","奥迪Q7","奥迪R18","奥迪R8","奥迪RS 3","奥迪RS 4","奥迪RS 5","奥迪RS 6","奥迪RS 7","奥迪RS Q3","奥迪S1","奥迪S3","奥迪S4","奥迪S5","奥迪S6","奥迪S7","奥迪S8","奥迪SQ5","奥迪SQ7","奥迪TT","奥迪TT offroad","奥迪TT RS","奥迪TTS","奥迪Urban"]
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
