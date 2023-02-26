package com.ch.mall.product.constant;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName ProductConstant.java
 * @Description TODO
 * @createTime 2022年11月21日 11:05:00
 */
public class ProductConstant {

    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "baseAttr"),
        ATTR_TYPE_SALE(0, "saleAttr");

        AttrEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private Integer code;
        private String msg;

        public Integer getCode() {
            return code;
        }
    }
}
