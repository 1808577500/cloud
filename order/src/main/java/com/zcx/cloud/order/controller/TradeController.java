package com.zcx.cloud.order.controller;

import com.zcx.cloud.order.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@RestController
public class TradeController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/hi")
    public String hello(String name) {
        return goodsService.hello();
    }

}
