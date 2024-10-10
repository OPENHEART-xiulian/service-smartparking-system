package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.SystemUserRole;
import org.springframework.stereotype.Repository;

/**
 * 系统管理-用户和角色关联表(SystemUserRole)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-14 09:20:50
 */
@Repository
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRole> {
}
