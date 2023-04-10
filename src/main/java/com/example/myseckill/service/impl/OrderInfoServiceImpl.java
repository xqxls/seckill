package com.example.myseckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myseckill.common.GlobalException;
import com.example.myseckill.enums.ResultEnum;
import com.example.myseckill.pojo.OrderInfo;
import com.example.myseckill.mapper.OrderInfoMapper;
import com.example.myseckill.pojo.SecKillGoods;
import com.example.myseckill.pojo.SecKillOrder;
import com.example.myseckill.pojo.User;
import com.example.myseckill.service.IGoodsService;
import com.example.myseckill.service.IOrderInfoService;
import com.example.myseckill.service.ISeckillGoodsService;
import com.example.myseckill.service.ISeckillOrderService;
import com.example.myseckill.vo.GoodsVo;
import com.example.myseckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author xqxls
 * @since 2023-03-14
 */
@Service
@Primary
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public OrderInfo secKill(User user, GoodsVo goodsVo) {
        // 秒杀商品表减库存
        SecKillGoods secKillGoods = seckillGoodsService.getOne(new QueryWrapper<SecKillGoods>().eq("goods_id", goodsVo.getId()));
        secKillGoods.setStockCount(secKillGoods.getStockCount() - 1);
        seckillGoodsService.update(new UpdateWrapper<SecKillGoods>()
                .setSql("stock_count = " + "stock_count-1")
                .eq("goods_id",goodsVo.getId())
                .gt("stock_count",0));

        if (secKillGoods.getStockCount() < 1) {
            //判断是否还有库存
            redisTemplate.opsForValue().set("isStockEmpty:" + goodsVo.getId(), "0");
            return null;
        }
        //生成订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(secKillGoods.getSecKillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        this.save(orderInfo);
        //生成秒杀订单
        SecKillOrder seckillOrder = new SecKillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goodsVo.getId(), seckillOrder);
        return orderInfo;
    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null) {
            throw new GlobalException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(orderInfo.getGoodsId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrderInfo(orderInfo);
        orderDetailVo.setGoodsVo(goodsVo);
        return orderDetailVo;
    }
}
