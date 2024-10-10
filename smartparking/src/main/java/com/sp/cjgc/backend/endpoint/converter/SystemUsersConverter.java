package com.sp.cjgc.backend.endpoint.converter;

import java.util.List;

import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.domain.query.SystemUsersQuery;
import com.sp.cjgc.backend.endpoint.request.SystemUsersReq;
import com.sp.cjgc.backend.endpoint.response.SystemUsersResp;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统管理-用户表(SystemUsers) 转换工具类
 *
 * @author zoey
 * @since 2024-08-13 15:25:13
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SystemUsersConverter {

    SystemUsersConverter INSTANCE = Mappers.getMapper(SystemUsersConverter.class);

    /**
     * 系统管理-用户表 数据新增入参转换成 SystemUsers 对象
     *
     * @param dto
     * @return
     */
    SystemUsers toEntity(SystemUsersReq.CreateOrUpdateDTO dto);

    /**
     * 用户表 数据更新入参转换成 SpUsers 对象
     *
     * @param updateDTO
     * @return
     */
    SystemUsers toSpUsers(SystemUsersReq.UpdateDTO updateDTO);

    /**
     * 用户表 查询条件入参转换成 SpUsersQuery 对象
     *
     * @param loginDTO
     * @return
     */
    SystemUsersQuery toQuery(SystemUsersReq.LoginDTO loginDTO);

    SystemUsersQuery toQuery(SystemUsersReq.UpdatePasswordDTO loginDTO);

    SystemUsersQuery toQuery(SystemUsersReq.QueryDTO loginDTO);

    /**
     * 系统管理-用户表 对象响应数据转换
     *
     * @param entity
     * @return
     */
    SystemUsersResp.SystemUsersDTO toDTO(SystemUsers entity);

    /**
     * 系统管理-用户表 列表响应数据转换
     *
     * @param list
     * @return
     */
    List<SystemUsersResp.SystemUsersDTO> toListDTO(List<SystemUsers> list);

    /**
     * 分页查询 系统管理-用户表 列表响应数据转换
     *
     * @param query
     * @return
     */
    SystemUsersResp.ListDTO toPage(PageInfoRespQuery query);
}
