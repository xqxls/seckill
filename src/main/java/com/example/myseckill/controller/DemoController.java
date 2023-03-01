package com.example.myseckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/3/1 23:33
 */
@Controller
@RequestMapping
public class DemoController {

    @GetMapping("/xqxls")
    public String hello(Model model){
        model.addAttribute("name"," world!");
        return "xqxls";
    }
}
