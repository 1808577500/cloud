package com.zcx.cloud.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "goods-server")
public interface GoodsService {

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    String hello();

}
