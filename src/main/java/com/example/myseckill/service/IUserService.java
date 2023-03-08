package com.example.myseckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myseckill.common.RespBean;
import com.example.myseckill.pojo.User;
import com.example.myseckill.vo.LoginVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author xqxls
 * @since 2023-03-07
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo);
}
