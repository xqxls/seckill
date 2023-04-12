package com.example.myseckill.access;

import com.example.myseckill.common.CommonResult;
import com.example.myseckill.config.UserContext;
import com.example.myseckill.enums.ResultEnum;
import com.example.myseckill.pojo.User;
import com.example.myseckill.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/12 22:03
 */
@Component
@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            log.info("拿到方法处理器…………");
            User user = UserUtil.getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int second = accessLimit.second();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(response, ResultEnum.SESSION_ERROR);
                }
                key += ":" + user.getId();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer count = (Integer) valueOperations.get(key);
            if (count == null) {
                valueOperations.set(key, 1, second, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                valueOperations.increment(key);
            } else {
                render(response, ResultEnum.ACCESS_LIMIT_REACHED);
                log.info("访问过于频繁，请稍后重试…………");
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, ResultEnum resultEnum) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        CommonResult commonResult = CommonResult.fail(resultEnum);
        printWriter.write(new ObjectMapper().writeValueAsString(commonResult));
        printWriter.flush();
        printWriter.close();
    }
}
