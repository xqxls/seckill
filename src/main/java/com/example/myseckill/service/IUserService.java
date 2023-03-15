package com.example.myseckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myseckill.common.CommonResult;
import com.example.myseckill.pojo.User;
import com.example.myseckill.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author xqxls
 * @since 2023-03-07
 */
public interface IUserService extends IService<User> {

    CommonResult doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
