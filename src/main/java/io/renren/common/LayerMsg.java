package io.renren.common;

import java.io.Serializable;
import java.util.List;

/**
 * @function  layui数据分页的显示
 */
public class LayerMsg implements Serializable{
	
    private int code;
    private String msg;
    private long total;
    private List data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public LayerMsg() {
    }


    public static LayerMsg success(long total, List data){
        LayerMsg layerMsg = new LayerMsg();
        layerMsg.setCode(0);
        layerMsg.setMsg("获取数据成功！");
        layerMsg.setTotal(total);
        layerMsg.setData(data);
        return layerMsg;
    }


    public static LayerMsg fail(){
        LayerMsg layerMsg = new LayerMsg();
        layerMsg.setCode(0);
        layerMsg.setMsg("获取数据成功！");
        layerMsg.setTotal(0);
        layerMsg.setData(null);
        return layerMsg;
    }

    public static LayerMsg fail(String msg){
        LayerMsg layerMsg = new LayerMsg();
        layerMsg.setCode(0);
        layerMsg.setMsg(msg);
        layerMsg.setTotal(0);
        layerMsg.setData(null);
        return layerMsg;
    }

}
