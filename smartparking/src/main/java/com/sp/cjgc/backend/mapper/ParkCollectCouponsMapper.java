package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商户-停车领劵(ParkCollectCoupons)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-22 09:56:37
 */
@Repository
public interface ParkCollectCouponsMapper extends BaseMapper<ParkCollectCoupons> {

    /**
     * 根据商户ID统计该商户有多少未使用的优惠券
     *
     * @param userId
     * @return
     */
    Long countTotal(@Param("userId") String userId);

    /**
     * 根据车牌号查询该车牌号对应的优惠券
     *
     * @param plates
     * @return
     */
    ParkCollectCoupons selectByPrimaryKey(@Param("plates") String plates);
}
