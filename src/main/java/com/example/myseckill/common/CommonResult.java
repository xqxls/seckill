package com.example.myseckill.common;

import com.example.myseckill.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/8 22:03
 */
public class CommonResult {

    private long code;
    private String message;
    private Object object;

    public static CommonResult success() {
        return new CommonResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    public static CommonResult success(Object object){
        return new CommonResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(),object);
    }

    public static CommonResult fail(ResultEnum resultEnum){
        return new CommonResult(resultEnum.getCode(), resultEnum.getMessage(),null);
    }

    public static CommonResult fail(ResultEnum resultEnum, Object object) {
        return new CommonResult(resultEnum.getCode(), resultEnum.getMessage(), object);
    }

    public CommonResult(long code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public CommonResult(){

    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
