package com.ygst.cenggeche.bean.notice;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class NoticeListBean {

    /**
     * data : [{"activityHeadImg":"77","id":2,"createTime":"2017-09-04 16:52:11","activiyDes":""},{"activityHeadImg":"999","id":1,"createTime":"2017-09-04 16:52:02","activiyDes":""}]
     * code : 0000
     * msg : 执行成功
     */

    private String code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * activityHeadImg : 77
         * id : 2
         * createTime : 2017-09-04 16:52:11
         * activiyDes :
         */

        private String activityHeadImg;
        private int id;
        private String createTime;
        private String activiyDes;

        public String getActivityHeadImg() {
            return activityHeadImg;
        }

        public void setActivityHeadImg(String activityHeadImg) {
            this.activityHeadImg = activityHeadImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getActiviyDes() {
            return activiyDes;
        }

        public void setActiviyDes(String activiyDes) {
            this.activiyDes = activiyDes;
        }
    }
}
