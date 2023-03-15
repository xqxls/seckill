package com.example.myseckill.controller;


import com.example.myseckill.common.CommonResult;
import com.example.myseckill.enums.ResultEnum;
import com.example.myseckill.pojo.User;
import com.example.myseckill.service.IOrderInfoService;
import com.example.myseckill.vo.OrderDeatilVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author xqxls
 * @since 2023-03-14
 */
@Controller
@RequestMapping("/order")
public class OrderInfoController {

    @Autowired
    private IOrderInfoService orderInfoService;


    @ApiOperation("订单")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult detail(User user, Long orderId) {
        if (user == null) {
            return CommonResult.fail(ResultEnum.SESSION_ERROR);
        }
        OrderDeatilVo orderDeatilVo = orderInfoService.detail(orderId);
        return CommonResult.success(orderDeatilVo);
    }
}
