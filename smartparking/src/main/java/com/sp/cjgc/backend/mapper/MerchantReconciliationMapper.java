package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.MerchantReconciliation;
import com.sp.cjgc.backend.domain.query.MerchantReconciliationQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商户-对账记录(MerchantReconciliation)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-20 13:13:44
 */
@Repository
public interface MerchantReconciliationMapper extends BaseMapper<MerchantReconciliation> {

    /**
     * 统计 商户-对账记录 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") MerchantReconciliationQuery query);

    /**
     * 分页查询 商户-对账记录 列表
     *
     * @param query
     * @return
     */
    List<MerchantReconciliation> getPageList(@Param("query") MerchantReconciliationQuery query);

    /**
     * 查询未对账的商家记录
     *
     * @param status
     * @param userId
     * @param yearNumber
     * @return
     */
    MerchantReconciliation getEntityByUserId(@Param("status") String status, @Param("userId") String userId, @Param("yearNumber") String yearNumber);
}
