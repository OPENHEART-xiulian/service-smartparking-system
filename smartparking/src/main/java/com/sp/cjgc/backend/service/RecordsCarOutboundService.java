package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.RecordsCarOutbound;
import com.sp.cjgc.backend.domain.query.RecordsCarOutboundQuery;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;

/**
 * 记录-车辆出场记录表(RecordsCarOutbound)表服务接口
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
public interface RecordsCarOutboundService extends IService<RecordsCarOutbound> {
    
    /**
     * 记录-车辆出场记录表-分页查询列表
     *
     * @param query
     * @return
     */
     PageInfoRespQuery getPageList(RecordsCarOutboundQuery query);
}
