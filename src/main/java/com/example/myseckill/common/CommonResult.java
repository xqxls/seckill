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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {

    private long code;
    private String message;
    private Object obj;

    public static CommonResult success() {
        return new CommonResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    public static CommonResult success(Object obj){
        return new CommonResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(),obj);
    }

    public static CommonResult fail(ResultEnum resultEnum){
        return new CommonResult(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage(),null);
    }
}
