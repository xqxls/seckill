package com.example.myseckill.common;

import com.example.myseckill.enums.RespBeanEnum;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/9 22:21
 */
public class GlobalException extends RuntimeException{

    private RespBeanEnum respBeanEnum;

    public RespBeanEnum getRespBeanEnum() {
        return respBeanEnum;
    }

    public void setRespBeanEnum(RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }

    public GlobalException(RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }
}
