package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.backend.domain.SystemRole;
import com.sp.cjgc.backend.domain.query.SystemRoleQuery;
import com.sp.cjgc.backend.mapper.SystemRoleMapper;
import com.sp.cjgc.backend.service.SystemRoleService;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统管理-角色表(SystemRole)表服务实现类
 *
 * @author zoey
 * @since 2024-08-13 15:25:27
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {

    /**
     * 系统管理-角色表-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(SystemRoleQuery query) {
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<SystemRole> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }
}
