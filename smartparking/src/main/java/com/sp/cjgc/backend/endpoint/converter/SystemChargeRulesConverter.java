package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.SystemChargeRules;
import com.sp.cjgc.backend.endpoint.request.SystemChargeRulesReq;
import com.sp.cjgc.backend.endpoint.response.SystemChargeRulesResp;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统管理-收费规则表(SystemChargeRules) 转换工具类
 *
 * @author zoey
 * @since 2024-08-15 14:32:03
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SystemChargeRulesConverter {

    SystemChargeRulesConverter INSTANCE = Mappers.getMapper(SystemChargeRulesConverter.class);

    /**
     * 系统管理-收费规则表 数据更新入参转换成 SystemChargeRules 对象
     *
     * @param dto
     * @return
     */
    SystemChargeRules toEntity(SystemChargeRulesReq.UpdateDTO dto);

    /**
     * 系统管理-收费规则表 对象响应数据转换
     *
     * @param entity
     * @return
     */
    SystemChargeRulesResp.SystemChargeRulesDTO toDTO(SystemChargeRules entity);
}