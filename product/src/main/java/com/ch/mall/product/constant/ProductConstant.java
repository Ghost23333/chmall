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

    public enum ProductStatusEnum {
        NEW_SPU(0,"新建"),
        SPU_UP(1,"商品上架"),
        SPU_DOWN(2,"商品下架"),
        ;

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ProductStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }
}
