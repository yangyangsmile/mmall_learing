package com.mmall.util;

import java.math.BigDecimal;

/**
 * Created by duanpengyang on 17-8-1.
 */
public class BigDecimalUtil {
    private BigDecimalUtil(){
    }
    public static   BigDecimal add(Double V1 , Double V2){
        BigDecimal b1 = new BigDecimal(Double.toString(V1));
        BigDecimal b2 = new BigDecimal(Double.toString(V2));
        return b1.add(b2);
    }
    public static   BigDecimal subtract(Double V1 , Double V2){
        BigDecimal b1 = new BigDecimal(Double.toString(V1));
        BigDecimal b2 = new BigDecimal(Double.toString(V2));
        return b1.subtract(b2);
    }

    public static   BigDecimal multiply(Double V1 , Double V2){
        BigDecimal b1 = new BigDecimal(Double.toString(V1));
        BigDecimal b2 = new BigDecimal(Double.toString(V2));
        return b1.multiply(b2);
    }
    public static   BigDecimal div(Double V1 , Double V2){
        BigDecimal b1 = new BigDecimal(Double.toString(V1));
        BigDecimal b2 = new BigDecimal(Double.toString(V2));
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
        //除不尽的
    }



}