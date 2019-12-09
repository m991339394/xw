package io.renren.common;

import java.io.Serializable;

/**
 * @author 
 * @date 
 **/
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1622636224535461965L;
    private T data;
    private Integer code;
    private String msg;

    public Result() {

    }

    public Result(T data) {
        this.data = data;
        this.code = 0;
        this.msg = "success";
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Result(T data, String msg) {
    	this.data = data;
        this.code = 0;
        this.msg = msg;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }
    
    public static <T> Result<T> success() {
    	return new Result<>(null);
    }

    public static <T> Result<T> success(T data,String msg) {
        return new Result<>(data,msg);
    }
    public static <T> Result<T> success(Integer code,String msg) {
    	return new Result<>(code,msg);
    }
    
    public static <T> Result<T> fail(String msg) {
        return new Result<>(1, msg);
    }
    
    public static <T> Result<T> fail() {
    	return new Result<>(1, "操作失败");
    }
    
    public static <T> Result<T> fail(Integer id) {
    	return new Result<>(id, "操作失败");
    }

    public static <T> Result<T> fail(Integer id, String msg) {
        return new Result<>(id, msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
