package com.ch.mall.ware.constant;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName ProductConstant.java
 * @Description TODO
 * @createTime 2022年11月21日 11:05:00
 */
public class PurchaseConstant {

    public enum PurchaseStatusEnum {
        CREATED(0, "created"),
        ASSIGNED(1, "assigned"),
        RECRIVE(2,"received"),
        FINISH(3,"finish"),
        HASERROR(4,"haserror");

        PurchaseStatusEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private Integer code;
        private String msg;

        public Integer getCode() {
            return code;
        }
    }

    public enum PurchaseDetailStatusEnum {
        CREATED(0, "created"),
        ASSIGNED(1, "assigned"),
        BUYING(2,"buying"),
        FINISH(3,"finish"),
        HASERROR(4,"haserror");

        PurchaseDetailStatusEnum(Integer code, String msg) {
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
