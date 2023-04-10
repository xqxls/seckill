package com.example.myseckill.rabbitmq;

import com.example.myseckill.pojo.SecKillOrder;
import com.example.myseckill.pojo.User;
import com.example.myseckill.service.IGoodsService;
import com.example.myseckill.service.IOrderInfoService;
import com.example.myseckill.util.JsonUtil;
import com.example.myseckill.vo.GoodsVo;
import com.example.myseckill.vo.SecKillMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/9 20:46
 */
@Service
@Slf4j
public class Consumer {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderInfoService orderInfoService;

    @RabbitListener(queues = "seckillQueue")
    public void receive(String message) {
        log.info("接收消息：" + message);
        SecKillMessageVo messageVo = JsonUtil.jsonStr2Object(message, SecKillMessageVo.class);
        if (Objects.isNull(messageVo)) {
            return;
        }
        Long goodsId = messageVo.getGoodsId();
        User user = messageVo.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {
            return;
        }
        //判断是否重复抢购
        SecKillOrder secKillOrder = (SecKillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (secKillOrder != null) {
            return;
        }
        //下单操作
        orderInfoService.secKill(user, goodsVo);
    }
}
