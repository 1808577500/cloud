package com.zcx.cloud.gateway.service.impl;

import com.zcx.cloud.gateway.feign.UserService;
import com.zcx.cloud.gateway.service.PerssionService;
import com.zcx.cloud.gateway.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @date 2019/8/15 23:03
 * @description: 接口.
 */
@Service("permissionService")
public class PerssionServiceImpl implements PerssionService {

    @Autowired
    private UserService userService;


    /**
     * @Description 校验权限
     **/
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if(!(authentication instanceof OAuth2Authentication)){
            return false;
        }
        return this.checkUserPermission(request,authentication);
    }

    private boolean checkUserPermission(HttpServletRequest request, Authentication authentication) {
        //获取用户的userId
        Integer userId = UserUtils.getUserId(JwtHelper.decode(UserUtils.getToken(request)).getClaims());
        String url = request.getRequestURI();
        String method = request.getMethod();
        userId = null == userId ? 0: userId;
        return userService.hasAuth(url, Long.parseLong(userId.toString()), method);
    }
}
