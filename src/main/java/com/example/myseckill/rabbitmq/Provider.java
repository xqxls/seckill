package com.example.myseckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/9 20:44
 */
@Service
@Slf4j
public class Provider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSeckillMessage(String message) {
        log.info("发送消息" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);
    }

    public void send(Object message) {
        log.info("发送消息" + message);
        rabbitTemplate.convertAndSend("queue",  message);
    }
}
