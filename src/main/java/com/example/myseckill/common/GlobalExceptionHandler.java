package com.example.myseckill.common;

import com.example.myseckill.enums.RespBeanEnum;
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
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException)e;
            return RespBean.fail(ex.getRespBeanEnum());
        }
        else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            RespBean respBean = RespBean.fail(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常：" + bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        System.out.println("异常信息" + e);
        return RespBean.fail(RespBeanEnum.ERROR);
    }
}
