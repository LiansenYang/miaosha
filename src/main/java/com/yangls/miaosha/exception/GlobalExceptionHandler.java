package com.yangls.miaosha.exception;

import com.yangls.miaosha.web.result.ResponseObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @description: 全局异常处理类
 * @author: yangLs
 * @create: 2020-06-02 20:53
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    //@ExceptionHandler 注解用来指明异常的处理类型，即如果这里指定为 GlobalException，则数组越界等异常就不会进到这个方法中来。
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResponseObject handlerGlobalException(Exception e){

        GlobalException ex = (GlobalException)e;
        ex.printStackTrace();

        return new ResponseObject(ex.getResponseStatus());
    }

    //其他异常跳转到错误页面，最好把错误日志栈输出到错误页面中
    //注意只能使用ModelAndView携带值，不能使用Map
    @ExceptionHandler(Exception.class)
    public ModelAndView  handlerOtherException(Throwable e, Model model) {
        e.printStackTrace();
        ModelAndView mv = new ModelAndView();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try{
            e.printStackTrace(pw);
            mv.addObject("message", sw.toString());
        } finally{
            pw.close();
        }
        mv.setViewName("myerror");
        return mv;
    }


}
