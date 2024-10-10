package com.sp.cjgc.backend.enums;

import lombok.Getter;

/**
 * @Author: Zoey
 * @Since: 2024-08-16 15:14:40
 * @Description:
 */
@Getter
public enum CarTypeEnum {

    // 车辆类型编码；1|内部车辆；2|所属企业公车；3|外部车辆(机械车位)；4|外部车辆(非机械车位)
    INTERNAL_CAR("1", "内部车辆"),
    ENTERPRISE_CAR("2", "所属企业公车"),
    EXTERNAL_CAR_MACHINERY("3", "外部车辆(机械车位)"),
    EXTERNAL_CAR_NO_MACHINERY("4", "外部车辆(非机械车位)");

    private final String code;
    private final String name;

    CarTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (CarTypeEnum carTypeEnum : CarTypeEnum.values()) {
            if (carTypeEnum.getCode().equals(code)) {
                return carTypeEnum.getName();
            }
        }
        return null;
    }
}
