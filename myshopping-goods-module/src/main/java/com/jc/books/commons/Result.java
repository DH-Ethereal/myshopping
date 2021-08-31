package com.jc.books.commons;

import lombok.Getter;

/**
 * @program: myshopping-project
 * @description: 结果处理对象
 * @author: hjc
 * @create: 2021-08-24 17:02
 **/
@Getter
public class Result {
    //成功失败
    private Boolean success;
    private String message;
    private Object data;

    private Result(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static Result success(){
        return new Result(true,null,null);
    }

    public static Result success(String message){
        return new Result(true,message,null);
    }

    public static Result success(String message,Object data){
        return new Result(true,message,data);
    }

    public static Result fail(){
        return new Result(false,null,null);
    }

    public static Result fail(String message){
        return new Result(false,message, null);
    }

    public static Result fail(String message,Object data){
        return new Result(false,message,data);
    }
}
