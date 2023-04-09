package com.example.myseckill.vo;

import com.example.myseckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/8 22:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {


    private User user;

    private GoodsVo goodsVo;

    private int secKillStatus;

    private int remainSeconds;


}
