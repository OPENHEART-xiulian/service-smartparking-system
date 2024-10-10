package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.SystemParkingDetail;
import com.sp.cjgc.backend.domain.query.SystemParkingDetailQuery;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;

/**
 * 系统管理-车位数量明细表(SystemParkingDetail)表服务接口
 *
 * @author zoey
 * @since 2024-08-15 16:24:56
 */
public interface SystemParkingDetailService extends IService<SystemParkingDetail> {

    /**
     * 系统管理-车位数量明细表-分页查询列表
     *
     * @param query
     * @return
     */
    PageInfoRespQuery getPageList(SystemParkingDetailQuery query);

    /**
     * 统计已分配车位总数量
     *
     * @return
     */
    Integer sumAssignedNumber();

    /**
     * 新增或更新
     *
     * @param entity
     * @return
     */
    SystemParkingDetail createOrUpdate(SystemParkingDetail entity);
}
