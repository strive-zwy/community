package com.zwy.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author zwy
 * @Date 2020/12/13 16:35
 * @Version 1.0
 * @Deacription TODO
 **/

@EnableAutoConfiguration
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
