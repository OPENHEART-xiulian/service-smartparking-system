package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.RecordCarEnter;
import com.sp.cjgc.backend.domain.query.RecordCarEnterQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 记录-车辆进场记录表(RecordCarEnter)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
@Repository
public interface RecordCarEnterMapper extends BaseMapper<RecordCarEnter> {

    /**
     * 统计 记录-车辆进场记录表 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") RecordCarEnterQuery query);

    /**
     * 分页查询 记录-车辆进场记录表 列表
     *
     * @param query
     * @return
     */
    List<RecordCarEnter> getPageList(@Param("query") RecordCarEnterQuery query);

    /**
     * 查询未结束订单中进场时间为最早的记录
     *
     * @return
     */
    RecordCarEnter getByPlates(@Param("groupId") Integer groupId, @Param("plates") String plates);

    /**
     * 统计已使用车位数量
     *
     * @return
     */
    Long getStatusCounts();

    /**
     * 查询未结束订单中进场时间为最早的记录
     *
     * @param license
     * @param deviceIp
     * @return
     */
    RecordCarEnter getByDeviceIp(@Param("license") String license, @Param("deviceIp") String deviceIp);
}
