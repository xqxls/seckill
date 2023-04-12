package com.example.myseckill.config;

import com.example.myseckill.pojo.User;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/12 21:51
 */
public class UserContext {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }
}
