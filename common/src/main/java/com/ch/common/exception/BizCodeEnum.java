package com.ch.common.exception;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName BizCodeEnum.java
 * @Description 全局异常的枚举，包括code和msg
 * @createTime 2022年11月14日 19:37:00
 */
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"数据校验异常");

    private int code;
    private String msg;


    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
