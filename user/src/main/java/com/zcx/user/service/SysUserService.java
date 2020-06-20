package com.zcx.user.service;

import com.zcx.user.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author pwl
 * @since 2019-09-03
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * @Description 检验权限
     * @Date 17:45 2019/9/9
     * @Param [url, userId, method]
     * @return java.lang.Boolean
     **/
    Boolean hasAuth(String url, Long userId, String method);

    int insertUpdate(SysUser sysUser);
}
