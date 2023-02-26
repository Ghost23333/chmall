package com.ch.common.exception;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName BizCodeEnum.java
 * @Description 全局异常的枚举，包括code和msg
 * @createTime 2022年11月14日 19:37:00
 *
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002：短信验证码频率太高
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *  15：用户
 *
 *
 *
 **/
 */
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"数据校验异常"),
    DUPLICATE_KEY_EXCEPTION(10002,"数据重复异常"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常");
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
