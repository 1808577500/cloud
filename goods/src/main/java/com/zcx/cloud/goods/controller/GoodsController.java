package com.zcx.cloud.goods.controller;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
public class GoodsController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(String name) {
        return name + ", Welcome to Spring Boot 2";
    }
}
