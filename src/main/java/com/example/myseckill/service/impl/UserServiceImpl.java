package com.example.myseckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myseckill.common.GlobalException;
import com.example.myseckill.common.CommonResult;
import com.example.myseckill.enums.ResultEnum;
import com.example.myseckill.pojo.User;
import com.example.myseckill.mapper.UserMapper;
import com.example.myseckill.service.IUserService;
import com.example.myseckill.util.CookieUtil;
import com.example.myseckill.util.MD5Util;
import com.example.myseckill.util.UUIDUtil;
import com.example.myseckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author xqxls
 * @since 2023-03-07
 */
@Service
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public CommonResult doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //参数校验
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//            return CommonResult.fail(ResultEnum.LOGIN_ERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)){
//            return CommonResult.fail(ResultEnum.MOBILE_ERROR);
//        }
        User user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        String userTicket = UUIDUtil.uuid();
        //将用户信息存入redis
        redisTemplate.opsForValue().set("user:" + userTicket, user);
        CookieUtil.setCookie(request, response, "userTicket", userTicket);
        return CommonResult.success(userTicket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }

    @Override
    public CommonResult updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user == null) {
            throw new GlobalException(ResultEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSlat()));
        int result = userMapper.updateById(user);
        if (1 == result) {
            //删除Redis
            redisTemplate.delete("user:" + userTicket);
            return CommonResult.success();
        }
        return CommonResult.fail(ResultEnum.PASSWORD_UPDATE_FAIL);
    }
}
