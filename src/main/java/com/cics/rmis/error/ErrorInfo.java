package com.cics.rmis.error;

/**
 * 异常信息实体
 * @param <T>
 */
public class ErrorInfo<T> {

    private Integer status;
    private String msg;
    private String url;
    private T data;

    private ErrorInfo(){

    }

    private ErrorInfo(Integer status, String msg, String url, T obj){
        this.status = status;
        this.msg = msg;
        this.url = url;
        this.data = obj;
    }

    public static ErrorInfo error(String msg){
        return new ErrorInfo(300, msg, "", null);
    }

    public static ErrorInfo error(String msg, String url){
        return new ErrorInfo(300, msg, url, null);
    }

    public static ErrorInfo error(String msg, String url, Object data){
        return new ErrorInfo(300, msg, url, data);
    }

    public Integer getStatus() {
        return status;
    }

    public ErrorInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ErrorInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ErrorInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public T getData() {
        return data;
    }

    public ErrorInfo setData(T data) {
        this.data = data;
        return this;
    }
}
