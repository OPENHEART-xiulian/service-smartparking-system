package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;

import java.util.Map;

/**
 * 商户-停车领劵(ParkCollectCoupons)表服务接口
 *
 * @author zoey
 * @since 2024-08-22 09:56:37
 */
public interface ParkCollectCouponsService extends IService<ParkCollectCoupons> {

    /**
     * 用户领劵
     *
     * @param userId        商户ID
     * @param licensePlates 车牌号/手机号
     */
    ParkCollectCoupons userCollectCoupons(String userId, String licensePlates, Long timestamp);

    /**
     * 获取商户可发放的车位数量
     *
     * @param userId
     * @return
     */
    Map<String, Object> assignedNumber(String userId);

    /**
     * 根据车牌号码获取优惠卷信息
     *
     * @param licensePlates
     * @return
     */
    ParkCollectCoupons getEntityByLicensePlates(String licensePlates);
}
