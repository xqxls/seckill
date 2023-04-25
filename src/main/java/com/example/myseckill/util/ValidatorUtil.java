package com.example.myseckill.util;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.regex.Pattern;

/**
 * 手机号码校验类
 *
 * @Author: huzhuo
 * @Date: Created in 2023/3/8 22:03
 */
public class ValidatorUtil {

    private static final Pattern mobile_patten = Pattern.compile("[1]([3-9])[0-9]{9}$");

    /**
     * 手机号码校验
     * @author LC
     * @operation add
     * @date 2:19 下午 2022/3/2
     * @param mobile
     * @return boolean
     **/
    public static boolean isMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        return true;
//        Matcher matcher = mobile_patten.matcher(mobile);
//        return matcher.matches();
    }
}
