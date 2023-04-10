package com.example.myseckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myseckill.pojo.OrderInfo;
import com.example.myseckill.pojo.User;
import com.example.myseckill.vo.GoodsVo;
import com.example.myseckill.vo.OrderDetailVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author xqxls
 * @since 2023-03-14
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    OrderInfo secKill(User user, GoodsVo goodsVo);

    OrderDetailVo detail(Long orderId);

    String createPath(User user, Long goodsId);

    boolean checkPath(User user, Long goodsId, String path);

}
