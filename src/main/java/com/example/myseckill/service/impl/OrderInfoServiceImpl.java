package com.example.myseckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myseckill.common.GlobalException;
import com.example.myseckill.enums.ResultEnum;
import com.example.myseckill.pojo.OrderInfo;
import com.example.myseckill.mapper.OrderInfoMapper;
import com.example.myseckill.pojo.SeckillGoods;
import com.example.myseckill.pojo.SeckillOrder;
import com.example.myseckill.pojo.User;
import com.example.myseckill.service.IGoodsService;
import com.example.myseckill.service.IOrderInfoService;
import com.example.myseckill.service.ISeckillGoodsService;
import com.example.myseckill.service.ISeckillOrderService;
import com.example.myseckill.vo.GoodsVo;
import com.example.myseckill.vo.OrderDeatilVo;
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
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goodsVo.getId()));
//        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count = " + "stock_count-1")
                .eq("goods_id",goodsVo.getId())
                .gt("stock_count",0));
        if(!result){
            return null;
        }
        //生成订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(seckillGoods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        this.save(orderInfo);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrderService.save(seckillOrder);
        return orderInfo;
    }

    @Override
    public OrderDeatilVo detail(Long orderId) {
        if (orderId == null) {
            throw new GlobalException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        GoodsVo goodsVobyGoodsId = goodsService.findGoodsVobyGoodsId(orderInfo.getGoodsId());
        OrderDeatilVo orderDeatilVo = new OrderDeatilVo();
        orderDeatilVo.setOrderInfo(orderInfo);
        orderDeatilVo.setGoodsVo(goodsVobyGoodsId);
        return orderDeatilVo;
    }
}
