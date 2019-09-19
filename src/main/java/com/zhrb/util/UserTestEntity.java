package com.zhrb.util;

import lombok.Data;

@Data
/**
 * 仅用于加密解密测试
 */
public class UserTestEntity {
    String userName ;

    String password ;

    String encodePwd;

    String salt ;
}
