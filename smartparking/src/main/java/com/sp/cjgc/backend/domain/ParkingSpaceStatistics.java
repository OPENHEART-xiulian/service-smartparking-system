package com.sp.cjgc.backend.domain;

import lombok.Data;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 13:21:20
 * @Description: 车位统计
 */
@Data
public class ParkingSpaceStatistics {

    private Long totalParkingSpaces;           // 总车位
    private Long remainingParkingSpaces;       // 剩余车位
    private Long usedParkingSpaces;            // 已用车位
    private Long rentalParkingSpace;           // 租赁车位
    private Long ordinaryParkingSpace;         // 普通车位
    private Long issuedParkingSpaces;          // 已发劵车位
    private Long unlicensedParkingSpaces;      // 未发劵车位
}
