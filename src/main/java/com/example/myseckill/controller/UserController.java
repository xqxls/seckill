package com.example.myseckill.controller;

import com.example.myseckill.common.CommonResult;
import com.example.myseckill.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author xqxls
 * @since 2023-03-07
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public CommonResult info(User user){
        return CommonResult.success(user);
    }
}
