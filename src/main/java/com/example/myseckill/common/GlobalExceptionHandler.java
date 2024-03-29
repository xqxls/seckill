package com.example.myseckill.common;

import com.example.myseckill.enums.ResultEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/9 22:24
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonResult ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException)e;
            return CommonResult.fail(ex.getResultEnum());
        }
        else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            CommonResult commonResult = CommonResult.fail(ResultEnum.BIND_ERROR);
            commonResult.setMessage("参数校验异常：" + bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return commonResult;
        }
        System.out.println("异常信息" + e);
        return CommonResult.fail(ResultEnum.ERROR);
    }
}
