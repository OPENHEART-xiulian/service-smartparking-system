package com.sp.cjgc.backend.enums;

import lombok.Getter;

/**
 * @Author: Zoey
 * @Since: 2024-08-16 09:38:24
 * @Description: 车辆所属类型枚举
 */
@Getter
public enum CarGroupEnum {

    //车辆分组编码，1｜临保；2｜月保；3｜访客车；4｜无车牌；5|其他；
    LIN_BAN(1, "临保"),
    YUE_BAN(2, "月保"),
    FANG_KA(3, "访客车"),
    WU_PAI_CHE(4, "无车牌"),
    QITA(5, "其他");

    private final Integer carGroupId;
    private final String carGroupName;

    CarGroupEnum(Integer carGroupId, String carGroupName) {
        this.carGroupId = carGroupId;
        this.carGroupName = carGroupName;
    }
}
