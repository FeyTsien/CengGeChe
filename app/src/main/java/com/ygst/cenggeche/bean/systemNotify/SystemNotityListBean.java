package com.ygst.cenggeche.bean.systemNotify;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SystemNotityListBean {
    /**
     * data : [{"id":1,"createTime":"2017-12-5 12:23:12","informDes":"hjkjkhjkhk","rList":[{"id":1,"informId":1,"resource":"123.jpg","rtype":1},{"id":2,"informId":1,"resource":"123.png","rtype":2}],"informStatus":"1","informTitle":"afjdllsaflfdlas","informType":"2"},{"id":2,"createTime":"2017-12-5 12:23:13","informDes":"hjkhjkhk","rList":[{"id":2,"informId":0,"resource":"","rtype":0}],"informStatus":"1","informTitle":"hjkjhjkjhkjjhkj","informType":"2"}]
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
         * id : 1
         * createTime : 2017-12-5 12:23:12
         * informDes : hjkjkhjkhk
         * rList : [{"id":1,"informId":1,"resource":"123.jpg","rtype":1},{"id":2,"informId":1,"resource":"123.png","rtype":2}]
         * informStatus : 1
         * informTitle : afjdllsaflfdlas
         * informType : 2
         */

        private int id;
        private String createTime;
        private String informDes;
        private String informStatus;
        private String informTitle;
        private String informType;
        private List<RListBean> rList;

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

        public String getInformDes() {
            return informDes;
        }

        public void setInformDes(String informDes) {
            this.informDes = informDes;
        }

        public String getInformStatus() {
            return informStatus;
        }

        public void setInformStatus(String informStatus) {
            this.informStatus = informStatus;
        }

        public String getInformTitle() {
            return informTitle;
        }

        public void setInformTitle(String informTitle) {
            this.informTitle = informTitle;
        }

        public String getInformType() {
            return informType;
        }

        public void setInformType(String informType) {
            this.informType = informType;
        }

        public List<RListBean> getRList() {
            return rList;
        }

        public void setRList(List<RListBean> rList) {
            this.rList = rList;
        }

        public static class RListBean {
            /**
             * id : 1
             * informId : 1
             * resource : 123.jpg
             * rtype : 1
             */

            private int id;
            private int informId;
            private String resource;
            private int rtype;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getInformId() {
                return informId;
            }

            public void setInformId(int informId) {
                this.informId = informId;
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
