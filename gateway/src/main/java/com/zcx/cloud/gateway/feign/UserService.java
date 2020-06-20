package com.zcx.cloud.gateway.feign;

import com.zcx.cloud.gateway.feign.fallback.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Pan Weilong
 * @date 2019/9/3 16:26
 * @description: 接口.
 */
@FeignClient(value = "vcloud-user-server",fallback = UserServiceImpl.class)
public interface UserService {

    /**
     *权限校验
     */
    @GetMapping("/sysUser/hasAuth")
    Boolean hasAuth(@RequestParam("url") String url, @RequestParam("userId") Long userId, @RequestParam("method") String method);

}
