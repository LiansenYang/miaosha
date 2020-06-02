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
        this(ResponseStatus.SUCCESS.getStatusCode(),
                ResponseStatus.SUCCESS.getStatusMsg(),
                data);
    }
    public ResponseObject(ResponseStatus responseStatus) {
        this(responseStatus.getStatusCode(),responseStatus.getStatusMsg());
    }

    public ResponseObject(String errorCode, String msg) {
        this(errorCode,msg,null);
    }

    public ResponseObject(String errorCode, String msg, T data) {
        this.errorCode = errorCode;
        this.msg = msg;
        if(ResponseStatus.SUCCESS.getStatusCode().equals(errorCode)){
            this.data = data;
        }
    }

    /**
     * 成功时候的调用
     * */
    public static <T> ResponseObject<T> success(T data){
        return new ResponseObject<T>(data);
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
