package com.ygst.cenggeche.bean.notice;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class NoticeInfoBean {

    /**
     * data : {"id":1,"activityHeadImg":"999","createTime":"2017-09-04 16:52:02","activityDes":"999","activityTitle":"999","rList":[{"activityId":1,"id":1,"resource":"1.jpg","rtype":1},{"activityId":1,"id":2,"resource":"11.mp4","rtype":2},{"activityId":1,"id":3,"resource":"","rtype":0}],"beginTime":"","activityStatus":"1","endTime":""}
     * code : 0000
     * msg : 执行成功
     */

    private DataBean data;
    private String code;
    private String msg;

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

    public static class DataBean {
        /**
         * id : 1
         * activityHeadImg : 999
         * createTime : 2017-09-04 16:52:02
         * activityDes : 999
         * activityTitle : 999
         * rList : [{"activityId":1,"id":1,"resource":"1.jpg","rtype":1},{"activityId":1,"id":2,"resource":"11.mp4","rtype":2},{"activityId":1,"id":3,"resource":"","rtype":0}]
         * beginTime :
         * activityStatus : 1
         * endTime :
         */

        private int id;
        private String activityHeadImg;
        private String createTime;
        private String activityDes;
        private String activityTitle;
        private String beginTime;
        private String activityStatus;
        private String endTime;
        private List<RListBean> rList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivityHeadImg() {
            return activityHeadImg;
        }

        public void setActivityHeadImg(String activityHeadImg) {
            this.activityHeadImg = activityHeadImg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getActivityDes() {
            return activityDes;
        }

        public void setActivityDes(String activityDes) {
            this.activityDes = activityDes;
        }

        public String getActivityTitle() {
            return activityTitle;
        }

        public void setActivityTitle(String activityTitle) {
            this.activityTitle = activityTitle;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getActivityStatus() {
            return activityStatus;
        }

        public void setActivityStatus(String activityStatus) {
            this.activityStatus = activityStatus;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public List<RListBean> getRList() {
            return rList;
        }

        public void setRList(List<RListBean> rList) {
            this.rList = rList;
        }

        public static class RListBean {
            /**
             * activityId : 1
             * id : 1
             * resource : 1.jpg
             * rtype : 1
             */

            private int activityId;
            private int id;
            private String resource;
            private int rtype;

            public int getActivityId() {
                return activityId;
            }

            public void setActivityId(int activityId) {
                this.activityId = activityId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getResource() {
                return resource;
            }

            public void setResource(String resource) {
                this.resource = resource;
            }

            public int getRtype() {
                return rtype;
            }

            public void setRtype(int rtype) {
                this.rtype = rtype;
            }
        }
    }
}
