package com.example.myseckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myseckill.pojo.Goods;
import com.example.myseckill.mapper.GoodsMapper;
import com.example.myseckill.pojo.User;
import com.example.myseckill.service.IGoodsService;
import com.example.myseckill.vo.DetailVo;
import com.example.myseckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author xqxls
 * @since 2023-03-14
 */
@Service
@Primary
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVobyGoodsId(Long goodsId) {
        return goodsMapper.findGoodsVobyGoodsId(goodsId);
    }

    @Override
    public DetailVo toDetail(User user, Long goodsId) {
        GoodsVo goodsVo = this.findGoodsVobyGoodsId(goodsId);
        //秒杀状态
        int seckillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        if (nowDate.after(endDate)) {
            //秒杀已经结束
            remainSeconds = -1;
            seckillStatus = 2;
        }
        else if(nowDate.before(startDate)) {
            //秒杀还未开始0
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        }
        else {
            //秒杀进行中
            seckillStatus = 1;
        }
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(seckillStatus);
        return detailVo;
    }
}
