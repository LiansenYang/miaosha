package com.yangls.miaosha.web.result;

import org.thymeleaf.util.StringUtils;

/**
 * @description: 返回数据状态
 * @author: yangLs
 * @create: 2020-06-01 10:54
 **/
public enum  ResponseStatus {
    //通用异常
    SUCCESS("0","success"),
    SERVER_ERROR("500100","server error"),

    //登录模块 5002XX
    PASSWORD_EMPTY("500211","登录密码不能为空"),
    MOBILE_EMPTY ("500212", "手机号不能为空"),
    USER_NOT_EXIST ("500213", "用户或密码错误，请重新输入");
    //登录模块 5002XX
    //商品模块 5003XX
    //订单模块 5004XX
    //秒杀模块 5005XX


    private String statusCode;

    private String statusMsg;

    ResponseStatus(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public String getStatusCode() {
        return statusCode;
    }

//    public void setStatusCode(String statusCode) {
//        this.statusCode = statusCode;
//    }

    public String getStatusMsg() {
        return statusMsg;
    }

//    public void setStatusMsg(String statusMsg) {
//        this.statusMsg = statusMsg;
//    }

    public static String getMsg(String code) {

        if(StringUtils.isEmpty(code)){
            return null;
        }
        for (ResponseStatus rs : ResponseStatus.values()) {
            if (rs.getStatusCode().equals(code)) {
                return rs.getStatusMsg();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(ResponseStatus.getMsg(ResponseStatus.SUCCESS.getStatusCode()));
    }
}
