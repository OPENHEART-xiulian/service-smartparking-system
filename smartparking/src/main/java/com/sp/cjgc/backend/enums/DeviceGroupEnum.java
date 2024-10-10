package com.sp.cjgc.backend.enums;

import lombok.Getter;

/**
 * @Author: Zoey
 * @Since: 2024-08-16 09:32:59
 * @Description: 设备组号枚举
 */
@Getter
public enum DeviceGroupEnum {

    THE_GATE("1", "主库"),
    EAST_GATE("2", "东库"),
    WEST_GATE("3", "西库"),
    ;

    private final String deviceGroupId;
    private final String deviceGroupName;

    DeviceGroupEnum(String deviceGroupId, String deviceGroupName) {
        this.deviceGroupId = deviceGroupId;
        this.deviceGroupName = deviceGroupName;
    }
}
