package com.example.myseckill.vo;

import com.example.myseckill.pojo.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单详情返回对象
 *
 * @author: LC
 * @date 2022/3/6 10:20 下午
 * @ClassName: OrderDetailVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {

    private OrderInfo orderInfo;

    private GoodsVo goodsVo;
}
