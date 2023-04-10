package com.example.myseckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myseckill.common.CommonResult;
import com.example.myseckill.enums.ResultEnum;
import com.example.myseckill.pojo.OrderInfo;
import com.example.myseckill.pojo.SecKillOrder;
import com.example.myseckill.pojo.User;
import com.example.myseckill.rabbitmq.Provider;
import com.example.myseckill.service.IGoodsService;
import com.example.myseckill.service.IOrderInfoService;
import com.example.myseckill.service.ISeckillOrderService;
import com.example.myseckill.util.JsonUtil;
import com.example.myseckill.vo.GoodsVo;
import com.example.myseckill.vo.SecKillMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/15 22:06
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
@Api(value = "秒杀", tags = "秒杀")
public class SecKillController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderInfoService orderService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private Provider mqSender;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisScript<Long> redisScript;

    private Map<Long, Boolean> emptyStockMap = new HashMap<>();

    @ApiOperation("秒杀功能(改造前)")
    @RequestMapping(value = "/doSeckill11", method = RequestMethod.POST)
    public String doSecKill2(Model model, User user, Long goodsId) {
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {
            model.addAttribute("errmsg", ResultEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢购
        SecKillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SecKillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrder != null) {
            model.addAttribute("errmsg", ResultEnum.REPEATE_ERROR.getMessage());
            return "secKillFail";
        }
        OrderInfo orderInfo = orderService.secKill(user, goodsVo);
        model.addAttribute("order", orderInfo);
        model.addAttribute("goods", goodsVo);
        return "orderDetail";
    }

    @ApiOperation("秒杀功能")
    @RequestMapping(value = "/{path}/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult doSecKill(@PathVariable String path,User user, Long goodsId) {
        if (user == null) {
            return CommonResult.fail(ResultEnum.SESSION_ERROR);
        }
        // 校验秒杀地址
        boolean check = orderService.checkPath(user, goodsId, path);
        if (!check) {
            return CommonResult.fail(ResultEnum.REQUEST_ILLEGAL);
        }
        // 判断是否重复抢购
        SecKillOrder seckillOrder = (SecKillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return CommonResult.fail(ResultEnum.REPEATE_ERROR);
        }
        // 内存标记，减少Redis的访问
        if (!CollectionUtils.isEmpty(emptyStockMap)&&emptyStockMap.get(goodsId)) {
            return CommonResult.fail(ResultEnum.EMPTY_STOCK);
        }
        // 预减库存
        Long stock = (Long) redisTemplate.execute(redisScript, Collections.singletonList("seckillGoods:" + goodsId), Collections.EMPTY_LIST);
        if (stock != null && stock < 0) {
            emptyStockMap.put(goodsId, true);
            redisTemplate.opsForValue().increment("seckillGoods:" + goodsId);
            return CommonResult.fail(ResultEnum.EMPTY_STOCK);
        }
        SecKillMessageVo message = new SecKillMessageVo(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(message));
        return CommonResult.success(0);
    }

    @ApiOperation("获取秒杀结果")
    @GetMapping("/getResult")
    @ResponseBody
    public CommonResult getResult(User user, Long goodsId) {
        if (user == null) {
            return CommonResult.fail(ResultEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return CommonResult.success(orderId);
    }

    @ApiOperation("获取秒杀地址")
    @GetMapping(value = "/path")
    @ResponseBody
    public CommonResult getPath(User user, Long goodsId, String captcha, HttpServletRequest request) {
        if (user == null) {
            return CommonResult.fail(ResultEnum.SESSION_ERROR);
        }

//        boolean check = orderService.checkCaptcha(user, goodsId, captcha);
//        if (!check) {
//            return CommonResult.fail(ResultEnum.ERROR_CAPTCHA);
//        }
        String str = orderService.createPath(user, goodsId);
        return CommonResult.success(str);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            emptyStockMap.put(goodsVo.getId(), false);
        });
    }
}
