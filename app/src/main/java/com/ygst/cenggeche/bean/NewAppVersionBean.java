package com.ygst.cenggeche.bean;

/**
 * Created by Administrator on 2017/9/15.
 */

public class NewAppVersionBean {

    /**
     * data : {"id":2,"platform":"ios","path":"www.baidu.com","versionName":"1.0","updateDate":"2017-09-10","changeLog":"","version":"1.0"}
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
         * id : 2
         * platform : ios
         * path : www.baidu.com
         * versionName : 1.0
         * updateDate : 2017-09-10
         * changeLog :
         * version : 1.0
         */

        private int id;
        private String platform;
        private String path;
        private String versionName;
        private String updateDate;
        private String changeLog;
        private String version;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getChangeLog() {
            return changeLog;
        }

        public void setChangeLog(String changeLog) {
            this.changeLog = changeLog;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
