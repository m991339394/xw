package io.renren.common;

import java.io.Serializable;

public class Resultd <T> implements Serializable {
	 private static final long serialVersionUID = 1622636224535461965L;
	    private T data;
	    private Integer code;
	    private String msg;

	    public Resultd() {

	    }

	    public Resultd(T data) {
	        this.data = data;
	        this.code = 0;
	        this.msg = "success";
	    }

	    public Resultd(Integer id, String msg) {
	        this.code = id;
	        this.msg = msg;
	    }
	    
	    public Resultd(T data, String msg) {
	    	this.data = data;
	        this.code = 1;
	        this.msg = msg;
	    }

	    public static <T> Resultd<T> success(T data) {
	        return new Resultd<>(data);
	    }

	    public static <T> Resultd<T> success(T data,String msg) {
	        return new Resultd<>(data,msg);
	    }
	    
	    public static <T> Resultd<T> fail(Integer id) {
	        return new Resultd<>(id, "操作失败");
	    }


	    public static <T> Resultd<T> fail(Integer id, String msg) {
	        return new Resultd<>(id, msg);
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
