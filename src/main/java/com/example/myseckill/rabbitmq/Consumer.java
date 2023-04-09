package com.example.myseckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/9 20:46
 */
@Service
@Slf4j
public class Consumer {

    @RabbitListener(queues = "queue")
    public void receive(Object message) {
        log.info("接收消息：" + message);
    }
}
