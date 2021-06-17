package com.liyk.server.utils;

import com.liyk.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author LiYongkui
 * @title: AdminUtil
 * @description: 操作员工具类
 * @date 2021-06-07 00712:38
 */
public class AdminUtil {
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
