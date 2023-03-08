package com.example.myseckill.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/8 22:29
 */
@Data
@ToString
public class LoginVo {

    @NotNull
    private String mobile;

    @NotNull
    private String password;
}
