package com.example.myseckill.vo;

import com.example.myseckill.pojo.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单详情返回对象
 *
 * @Author: huzhuo
 * @Date: Created in 2023/3/8 22:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeatilVo {

    private OrderInfo orderInfo;

    private GoodsVo goodsVo;
}
