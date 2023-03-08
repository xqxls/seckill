package com.example.myseckill.common;

import com.example.myseckill.enums.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/8 22:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object obj;

    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),obj);
    }

    public static RespBean fail(RespBeanEnum respBeanEnum){
        return new RespBean(RespBeanEnum.ERROR.getCode(),RespBeanEnum.ERROR.getMessage(),null);
    }
}
