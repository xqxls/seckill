package com.example.myseckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myseckill.enums.ResultEnum;
import com.example.myseckill.pojo.OrderInfo;
import com.example.myseckill.pojo.SeckillOrder;
import com.example.myseckill.pojo.User;
import com.example.myseckill.service.IGoodsService;
import com.example.myseckill.service.IOrderInfoService;
import com.example.myseckill.service.ISeckillOrderService;
import com.example.myseckill.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/15 22:06
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
@Api(value = "秒杀", tags = "秒杀")
public class SeckillController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderInfoService orderService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @ApiOperation("秒杀功能")
    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    public String doSecKill2(Model model, User user, Long goodsId) {
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVobyGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {
            model.addAttribute("errmsg", ResultEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrder != null) {
            model.addAttribute("errmsg", ResultEnum.REPEATE_ERROR.getMessage());
            return "secKillFail";
        }
        OrderInfo orderInfo = orderService.secKill(user, goodsVo);
        model.addAttribute("order", orderInfo);
        model.addAttribute("goods", goodsVo);
        return "orderDetail";
    }
}
