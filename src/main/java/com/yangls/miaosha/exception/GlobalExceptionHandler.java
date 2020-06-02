package com.yangls.miaosha.exception;

import com.yangls.miaosha.web.result.ResponseObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局异常处理类
 * @author: yangLs
 * @create: 2020-06-02 20:53
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    //@ExceptionHandler 注解用来指明异常的处理类型，即如果这里指定为 GlobalException，则数组越界等异常就不会进到这个方法中来。
    @ExceptionHandler(GlobalException.class)
    public ResponseObject handlerException(Exception e){
        GlobalException ex = (GlobalException)e;
        return new ResponseObject(ex.getResponseStatus());
    }

}
