package com.yangls.miaosha.exception;

import com.yangls.miaosha.web.result.ResponseStatus;

/**
 * @description: 全局异常处理
 * @author: yangLs
 * @create: 2020-06-02 20:29
 **/
public class GlobalException extends RuntimeException {

    private ResponseStatus responseStatus;

    public GlobalException(ResponseStatus responseStatus) {
        super(responseStatus.getStatusMsg());
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

//    public void setResponseStatus(ResponseStatus responseStatus) {
//        this.responseStatus = responseStatus;
//    }
}
