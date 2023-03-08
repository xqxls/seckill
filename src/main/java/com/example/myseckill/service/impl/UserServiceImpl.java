package com.example.myseckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myseckill.common.RespBean;
import com.example.myseckill.enums.RespBeanEnum;
import com.example.myseckill.pojo.User;
import com.example.myseckill.mapper.UserMapper;
import com.example.myseckill.service.IUserService;
import com.example.myseckill.util.MD5Util;
import com.example.myseckill.util.ValidatorUtil;
import com.example.myseckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //参数校验
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return RespBean.fail(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(mobile)){
            return RespBean.fail(RespBeanEnum.MOBILE_ERROR);
        }

        User user = userMapper.selectById(mobile);
        if (user == null) {
            return RespBean.fail(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            return RespBean.fail(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}
