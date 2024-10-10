package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.CarVisitor;
import com.sp.cjgc.backend.domain.query.CarVisitorQuery;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;

/**
 * 车辆管理-访客车登记管理表(CarVisitor)表服务接口
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
public interface CarVisitorService extends IService<CarVisitor> {
    
    /**
     * 车辆管理-访客车登记管理表-分页查询列表
     *
     * @param query
     * @return
     */
     PageInfoRespQuery getPageList(CarVisitorQuery query);

    /**
     * 根据内地车牌查询是否存在访客车
     *
     * @param mainlandLicensePlates
     */
    CarVisitor getByLicensePlates(String mainlandLicensePlates);

    /**
     * 车辆管理-访客车登记管理表-新增或编辑
     *
     * @param entity
     * @return
     */
    void createOrUpdate(CarVisitor entity);
}
