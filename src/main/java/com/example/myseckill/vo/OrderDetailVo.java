package com.example.myseckill.vo;

import com.example.myseckill.pojo.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单详情返回对象
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/12 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {

    private OrderInfo orderInfo;

    private GoodsVo goodsVo;
}
