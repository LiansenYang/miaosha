package com.yangls.miaosha.web.result;

/**
 * @description: json返回对象
 * @author: yangLs
 * @create: 2020-06-01 10:42
 *
 **/
public class ResponseObject<T> {
    private String errorCode;

    private String msg;

    private T data;

    public ResponseObject(T data) {
        this(ResponseStatus.SUCCESS,data);
    }
    public ResponseObject(ResponseStatus responseStatus) {
        this(responseStatus,null);
    }
    public ResponseObject(ResponseStatus responseStatus,T data) {
       this.errorCode = responseStatus.getStatusCode();
       this.msg = responseStatus.getStatusMsg();
       this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
