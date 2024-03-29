package com.example.myseckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myseckill.pojo.Goods;
import com.example.myseckill.pojo.User;
import com.example.myseckill.vo.DetailVo;
import com.example.myseckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author xqxls
 * @since 2023-03-14
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVobyGoodsId(Long goodsId);

    DetailVo toDetail(User user, Long goodsId);
}
