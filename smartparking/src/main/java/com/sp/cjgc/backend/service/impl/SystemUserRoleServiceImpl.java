package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.backend.domain.SystemUserRole;
import com.sp.cjgc.backend.mapper.SystemUserRoleMapper;
import com.sp.cjgc.backend.service.SystemUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 系统管理-用户和角色关联表(SystemUserRole)表服务实现类
 *
 * @author zoey
 * @since 2024-08-14 09:20:53
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRole> implements SystemUserRoleService {

}
