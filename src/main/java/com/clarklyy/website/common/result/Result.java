package com.clarklyy.website.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static Result result(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(Object data){
        return result(200, "操作成功", data);
    }

    public static Result success(String msg){
        return result(200, msg, null);
    }

    public static Result fail(String msg){
        String data="";
        return result(400, msg, data);
    }
}
