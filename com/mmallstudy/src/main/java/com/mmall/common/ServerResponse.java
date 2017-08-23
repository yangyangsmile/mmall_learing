package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by duanpengyang on 17-7-25.
 */

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable{
    private int  status;
    private String msg;
    private T date;


    private ServerResponse(int status){
        this.status = status;

    }
    private ServerResponse(int status,T date){
        this.status = status;
        this.date = date;
    }
    private ServerResponse (int status, String msg ,T date){
        this.status = status;
        this.msg = msg;
        this.date = date;
    }

    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    @JsonIgnore
   public boolean isSuccess(){
       return this.status ==ResponseCode.SUCCSEE.getCode();
   }

    public  int getStatus(){
        return  status;
    }
    public String getMsg(){
        return msg;
    }
    public T getDate(){
        return date;
    }



    public static <T>ServerResponse<T> CreateBySuccess(T date ){
            return new ServerResponse<T>(ResponseCode.SUCCSEE.getCode(),date);
    }
    public static <T>ServerResponse<T> CreateBySuccess(String msg,T date){
        return new ServerResponse(ResponseCode.SUCCSEE.getCode(),msg,date);
    }


    public static <T> ServerResponse<T>CreateBySuccess(){
        return new ServerResponse(ResponseCode.SUCCSEE.getCode());
    }
    public static <T> ServerResponse<T> CreateBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCSEE.getCode(),msg);
    }
    public static <T>ServerResponse<T> CreateByError( ){
        return new ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMsg());
    }
    public static <T>ServerResponse<T> CreateByErrorMessage(String errorMsg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }
    public static <T>ServerResponse<T> CreateByErrorCodeMessage(int code,String msg){
        return new ServerResponse(code,msg);
    }










}
