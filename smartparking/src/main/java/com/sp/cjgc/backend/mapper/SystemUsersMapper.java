package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.domain.query.SystemUsersQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统管理-用户表(SystemUsers)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-13 15:25:10
 */
@Repository
public interface SystemUsersMapper extends BaseMapper<SystemUsers> {

    /**
     * 统计 系统管理-用户表 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") SystemUsersQuery query);

    /**
     * 分页查询 系统管理-用户表 列表
     *
     * @param query
     * @return
     */
    List<SystemUsers> getPageList(@Param("query") SystemUsersQuery query);

    /**
     * 根据账号查询用户是否存在，且是否唯一
     *
     * @param account
     * @return
     */
    Long accountDoesItExist(@Param("account") String account);

    /**
     * 查询用户详细信息
     *
     * @param account
     * @return
     */
    SystemUsers getEntityByAccount(@Param("account") String account);

    /**
     * 根据id查询用户详细信息
     *
     * @param userId
     * @return
     */
    SystemUsers getEntityByUserId(@Param("userId") String userId);

    /**
     * 查询管理员用户
     *
     * @return
     */
    SystemUsers getEntity();
}
