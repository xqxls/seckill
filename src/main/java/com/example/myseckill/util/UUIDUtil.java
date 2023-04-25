package com.example.myseckill.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @Author: huzhuo
 * @Date: Created in 2023/3/8 22:03
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
