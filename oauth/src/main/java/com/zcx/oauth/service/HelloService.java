package com.zcx.oauth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Pan Weilong
 * @date 2019/9/2 18:17
 * @description: 接口.
 */
@FeignClient(value = "vcloud-order-server")
public interface HelloService {

    @GetMapping("/hello")
    public String getHello();

}
