package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.SystemRole;
import com.sp.cjgc.backend.domain.query.SystemRoleQuery;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;

/**
 * 系统管理-角色表(SystemRole)表服务接口
 *
 * @author zoey
 * @since 2024-08-13 15:25:27
 */
public interface SystemRoleService extends IService<SystemRole> {

    /**
     * 系统管理-角色表-分页查询列表
     *
     * @param query
     * @return
     */
    PageInfoRespQuery getPageList(SystemRoleQuery query);
}
