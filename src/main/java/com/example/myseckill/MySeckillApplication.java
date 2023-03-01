package com.example.myseckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.myseckill.pojo")
public class MySeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySeckillApplication.class, args);
    }

}
