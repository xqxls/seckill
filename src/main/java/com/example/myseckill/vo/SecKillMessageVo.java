package com.example.myseckill.vo;

import com.example.myseckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/9 22:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecKillMessageVo implements Serializable {

    private final Long uid = 1L;

    private User user;

    private Long goodsId;
}
