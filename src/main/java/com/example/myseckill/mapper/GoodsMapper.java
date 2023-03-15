package com.example.myseckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myseckill.pojo.Goods;
import com.example.myseckill.vo.GoodsVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author xqxls
 * @since 2023-03-14
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVobyGoodsId(Long goodsId);
}
