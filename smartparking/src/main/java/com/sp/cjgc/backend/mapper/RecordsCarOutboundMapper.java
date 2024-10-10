package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.RecordsCarOutbound;
import com.sp.cjgc.backend.domain.query.RecordsCarOutboundQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 记录-车辆出场记录表(RecordsCarOutbound)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
@Repository
public interface RecordsCarOutboundMapper extends BaseMapper<RecordsCarOutbound> {

    /**
     * 统计 记录-车辆出场记录表 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") RecordsCarOutboundQuery query);

    /**
     * 分页查询 记录-车辆出场记录表 列表
     *
     * @param query
     * @return
     */
    List<RecordsCarOutbound> getPageList(@Param("query") RecordsCarOutboundQuery query);
}
