package com.sp.cjgc.backend.utils;

import com.sp.cjgc.backend.domain.ParkingSpaceStatistics;
import com.sp.cjgc.backend.domain.SystemParking;
import com.sp.cjgc.backend.domain.SystemParkingDetail;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.enums.RoleEnum;
import com.sp.cjgc.backend.mapper.ParkCollectCouponsMapper;
import com.sp.cjgc.backend.mapper.RecordCarEnterMapper;
import com.sp.cjgc.backend.mapper.SystemParkingDetailMapper;
import com.sp.cjgc.backend.service.SystemParkingDetailService;
import com.sp.cjgc.backend.service.SystemParkingService;
import com.sp.cjgc.backend.service.SystemUsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 13:53:15
 * @Description:
 */
@Component
public class StatisticUtils {

    @Autowired
    private SystemParkingService systemParkingService;
    @Autowired
    private RecordCarEnterMapper recordCarEnterMapper;
    @Autowired
    private SystemUsersService systemUsersService;
    @Autowired
    private ParkCollectCouponsMapper parkCollectCouponsMapper;
    @Autowired
    private SystemParkingDetailMapper systemParkingDetailMapper;
    @Autowired
    private SystemParkingDetailService systemParkingDetailService;

    /**
     * 首页-车位统计
     *
     * @param userId 商户ID
     * @return
     */
    public ParkingSpaceStatistics parkingSpaceStatistics(String userId) {
        // 创建对象
        ParkingSpaceStatistics statistics = new ParkingSpaceStatistics();
        // 定义总车位为0
        Long totalParkingSpaces = 0L;
        // 判断是否存在用户ID
        if (StringUtils.isNotBlank(userId)) {
            // 查询用户信息
            SystemUsers users = systemUsersService.getEntityByAccountOrId(userId);
            // 判断用户角色是否是非管理员
            if (RoleEnum.GL.getRoleId().equals(users.getRoleId())
                    || RoleEnum.CJ.getRoleId().equals(users.getRoleId())
            ) userId = "";
        }

        // 判断是否存在用户ID --- 商户的首页
        if (StringUtils.isNotBlank(userId)) {
            // 查询该用户的租赁车位总数
            SystemParkingDetail entityByUserId = systemParkingDetailMapper.getEntityByUserId(userId);
            // 获取该用户的租赁车位总数
            if (Objects.nonNull(entityByUserId)) totalParkingSpaces = entityByUserId.getAssignedNumber().longValue();
            // 赋值总车位
            statistics.setTotalParkingSpaces(totalParkingSpaces);
            // 未发劵车位
            statistics.setUnlicensedParkingSpaces(0L);
            // 已发劵车位
            statistics.setIssuedParkingSpaces(0L);
            if (totalParkingSpaces > 0L) {
                // 获取已发放车位数量
                Long countTotal = parkCollectCouponsMapper.countTotal(userId) == null ? 0L : parkCollectCouponsMapper.countTotal(userId);
                // 已发劵车位
                statistics.setIssuedParkingSpaces(countTotal);
                // 未发劵车位
                statistics.setUnlicensedParkingSpaces(totalParkingSpaces - countTotal);
            }
            // 已用车位
            statistics.setUsedParkingSpaces(0L);
            // 剩余车位
            statistics.setRemainingParkingSpaces(0L);
            // 普通车位
            statistics.setOrdinaryParkingSpace(0L);
            // 租赁车位
            statistics.setRentalParkingSpace(0L);
        } else {
            // 管理员的首页
            // 查询总车位
            SystemParking parking = systemParkingService.getEntity();
            // 判断是否存在数据 获取总车位
            if (Objects.nonNull(parking)) totalParkingSpaces = parking.getParkingNumber().longValue();
            // 赋值总车位
            statistics.setTotalParkingSpaces(totalParkingSpaces);
            // 未发劵车位
            statistics.setUnlicensedParkingSpaces(0L);
            // 已发劵车位
            statistics.setIssuedParkingSpaces(0L);
            // 判断总车位是否为0
            if (totalParkingSpaces == 0L) {
                // 已用车位
                statistics.setUsedParkingSpaces(0L);
                // 剩余车位
                statistics.setRemainingParkingSpaces(0L);
                // 普通车位
                statistics.setOrdinaryParkingSpace(0L);
                // 租赁车位
                statistics.setRentalParkingSpace(0L);
            } else {
                // 统计已用车位
                Long statusCounts = recordCarEnterMapper.getStatusCounts() == null ? 0L : recordCarEnterMapper.getStatusCounts();
                // 已用车位
                statistics.setUsedParkingSpaces(statusCounts);
                // 剩余车位
                statistics.setRemainingParkingSpaces(totalParkingSpaces - statusCounts);
                // 获取已发放车位数量
                Long countTotal = parkCollectCouponsMapper.countTotal(userId) == null ? 0L : parkCollectCouponsMapper.countTotal(userId);
                statistics.setIssuedParkingSpaces(countTotal);
                // 租赁车位
                Integer assignedNumber = systemParkingDetailService.sumAssignedNumber();
                statistics.setRentalParkingSpace(assignedNumber == null ? 0 : assignedNumber.longValue());
                // 普通车位
                statistics.setOrdinaryParkingSpace(statusCounts - countTotal);
            }
        }
        return statistics;
    }
}
