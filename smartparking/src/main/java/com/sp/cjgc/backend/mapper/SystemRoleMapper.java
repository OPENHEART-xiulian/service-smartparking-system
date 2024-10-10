package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.SystemRole;
import com.sp.cjgc.backend.domain.query.SystemRoleQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统管理-角色表(SystemRole)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-13 15:25:27
 */
@Repository
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    /**
     * 统计 系统管理-角色表 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") SystemRoleQuery query);
    
    /**
     * 分页查询 系统管理-角色表 列表
     *
     * @param query
     * @return
     */
    List<SystemRole> getPageList(@Param("query") SystemRoleQuery query);
}
