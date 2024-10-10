package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.SystemChargeRules;

/**
 * 系统管理-收费规则表(SystemChargeRules)表服务接口
 *
 * @author zoey
 * @since 2024-08-15 14:32:03
 */
public interface SystemChargeRulesService extends IService<SystemChargeRules> {

    SystemChargeRules getEntity();

    SystemChargeRules updateEntity(SystemChargeRules entity);
}
