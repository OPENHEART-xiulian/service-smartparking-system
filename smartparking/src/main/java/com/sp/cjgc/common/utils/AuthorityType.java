package com.sp.cjgc.common.utils;

/**
 * @Author: Zoey
 * @Date: 2023/7/12
 * @Time: 下午5:21
 * @Describe: 接口类型
 */
public class AuthorityType {

    /**
     * 1|增加 2|修改
     */
    public static final String CREATE = "1,2";

    /**
     * 删除
     */
    public static final String DELETE = "3";

    /**
     * 查询
     */
    public static final String READ = "4";

    /**
     * 超级管理员权限
     */
    public static final String SUPER = "6";
}
