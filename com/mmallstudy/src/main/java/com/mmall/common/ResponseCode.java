package com.mmall.common;

/**
 * Created by duanpengyang on 17-7-25.
 */
public enum  ResponseCode {
        SUCCSEE(0,"SUCCSEE"),
        ERROR(1,"ERROR"),
        NEED_LOGIN(10,"NEED_LOGIN"),
        ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private  final int code;
    private final String msg;
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

     ResponseCode(int code , String msg){
        this.code = code;
        this.msg = msg;
    }

}
