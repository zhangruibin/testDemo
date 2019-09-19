package com.zhrb.util;

import com.yyx.aio.common.enumeration.AccessEnum;
import com.yyx.aio.entity.User;
import com.yyx.aio.security.UrlGrantedAuthority;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * @author wuwei
 * @time 2017 12 01
 */
public class UserUtil {
    /*
      * 获取当前用户
      *
      * @return
      */
    public static User getCurrentUser() {
        User user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;
        if (authentication != null) {
            principal = authentication.getPrincipal();
        }
        if (principal != null && principal instanceof User) {
            user = (User) principal;
        }
        return user;
    }
    public static String getOrg(){
        if (getCurrentUser() != null){
            if(StringUtils.isEmpty(getCurrentUser().getDistCd())){
                return "";
            }
            return getCurrentUser().getDistCd().substring(0,2);
        }
        return null;
    }

    /**
     * 获取访问数据权限
     * @return
     */
    public static Integer getDataAccessAuthority(){
        Integer access = AccessEnum.DATA_ACCESS_MAX.getCode();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for(GrantedAuthority authority:authorities){
            if(authority instanceof UrlGrantedAuthority){
                UrlGrantedAuthority urlGrantedAuthority= (UrlGrantedAuthority) authority;
                AccessEnum val = AccessEnum.val(urlGrantedAuthority.getPermissionUrl());
                if(val != null && access>val.getCode()){
                    access =val.getCode();
                }
            }
        }
        return access;
    }
}
