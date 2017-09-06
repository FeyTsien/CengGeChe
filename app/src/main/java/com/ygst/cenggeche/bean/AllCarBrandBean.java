package com.ygst.cenggeche.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class AllCarBrandBean {

    private List<BrandBean> brand;

    public List<BrandBean> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandBean> brand) {
        this.brand = brand;
    }

    public static class BrandBean {
        /**
         * brand : 阿尔法罗密欧
         * initials : A
         */

        private String brand;
        private String initials;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getInitials() {
            return initials;
        }

        public void setInitials(String initials) {
            this.initials = initials;
        }
    }
}
