package io.renren.common;

import java.util.List;

public class QueryResult<T> extends Result<T> {
    private int total;
    private int pageSize;
    private int pageNo;

    QueryResult(T data, int total, int pageNo, int pageSize) {
        super(data);
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    QueryResult(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public static <T> QueryResult<T> success(T data, int total, int pageNo, int pageSize) {
        return new QueryResult<>(data, total, pageNo, pageSize);
    }
    
    public static <T> QueryResult<T> success(List data, int total) {
    	return new QueryResult(data, total, 0 ,0);
    }



}
