package com.ch.mall.product.exception;

import com.ch.common.exception.BizCodeEnum;
import com.ch.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName ChmailExceptionControllerAdvice.java
 * @Description 集中处理所有异常
 * @createTime 2022年11月14日 11:51:00
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.ch.mall.product.controller"})
public class ChmailExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            HashMap<String, String> map = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                String message = fieldError.getDefaultMessage();
                String field = fieldError.getField();
                map.put(field, message);
            }
            return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg())
                    .put("data", map);
        }
        return R.error();
    }

    @ExceptionHandler(value = Throwable.class)
    public R handlerException(Throwable throwable) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
