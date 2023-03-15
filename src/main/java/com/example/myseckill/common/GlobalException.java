package com.example.myseckill.common;

import com.example.myseckill.enums.ResultEnum;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/9 22:21
 */
public class GlobalException extends RuntimeException{

    private ResultEnum resultEnum;

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public GlobalException(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }
}
