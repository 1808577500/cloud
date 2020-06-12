package com.zcx.oauth.bean;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangcx
 * @date 2020/6/12 16:29
 * @description: 接口.
 */
@Data
@AllArgsConstructor
public class UserVo implements Serializable {

    private Long userId;
    private String username;
    private String password;
}
