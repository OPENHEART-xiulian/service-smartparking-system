package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.CarVpiMonthlyRent;
import com.sp.cjgc.backend.domain.query.CarVpiMonthlyRentQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆管理-vip月租车登记管理表(CarVpiMonthlyRent)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
@Repository
public interface CarVpiMonthlyRentMapper extends BaseMapper<CarVpiMonthlyRent> {

    /**
     * 统计 车辆管理-vip月租车登记管理表 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") CarVpiMonthlyRentQuery query);

    /**
     * 分页查询 车辆管理-vip月租车登记管理表 列表
     *
     * @param query
     * @return
     */
    List<CarVpiMonthlyRent> getPageList(@Param("query") CarVpiMonthlyRentQuery query);

    /**
     * 根据车牌号码 查询数据是否存在
     *
     * @param mainlandLicensePlates
     * @return
     */
    CarVpiMonthlyRent getByLicensePlates(@Param("mainlandLicensePlates") String mainlandLicensePlates);
}
