package com.sp.cjgc.backend.enums;

import lombok.Getter;

/**
 * @Author: Zoey
 * @Date: 2023/7/13
 * @Time: 上午9:16
 * @Describe: 角色枚举 -- 需要匹配数据库,可自定义
 */
@Getter
public enum RoleEnum {

    JL("1", "vip", "酒店用户"),
    FX("2", "ordinary", "商户用户"),
    GL("3", "admin", "管理员"),
    CJ("4", "super", "超级管理员"),
    ;


    private final String roleId;

    private final String roleCode;

    private final String roleName;

    RoleEnum(String roleId, String roleCode, String roleName) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public static String getRoleName(String roleId) {
        for (RoleEnum v : RoleEnum.values()) {
            if (roleId.equals(v.getRoleId())) {
                return v.roleName;
            }
        }
        return roleId;
    }
}
