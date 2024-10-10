package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.CarVisitor;
import com.sp.cjgc.backend.domain.query.CarVisitorQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆管理-访客车登记管理表(CarVisitor)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
@Repository
public interface CarVisitorMapper extends BaseMapper<CarVisitor> {

    /**
     * 统计 车辆管理-访客车登记管理表 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") CarVisitorQuery query);

    /**
     * 分页查询 车辆管理-访客车登记管理表 列表
     *
     * @param query
     * @return
     */
    List<CarVisitor> getPageList(@Param("query") CarVisitorQuery query);

    /**
     * 根据内地车牌号码查询数据是否存在
     *
     * @param mainlandLicensePlates
     * @return
     */
    CarVisitor getByLicensePlates(@Param("mainlandLicensePlates") String mainlandLicensePlates);

    /**
     * 根据内地车牌号码查询是否免费
     *
     * @param mainlandLicensePlates
     * @return
     */
    CarVisitor getIsFreeByLicensePlates(@Param("mainlandLicensePlates") String mainlandLicensePlates);
}
