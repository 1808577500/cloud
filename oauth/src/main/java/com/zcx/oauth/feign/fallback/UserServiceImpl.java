package com.zcx.oauth.feign.fallback;

import com.zcx.oauth.bean.UserVo;
import com.zcx.oauth.feign.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Pan Weilong
 * @date 2019/9/3 16:32
 * @description: 接口.
 */
@Service
public class UserServiceImpl extends UserService {

    @Override
    public UserVo getUserByUsername(String username) {
        return null;
    }
}
