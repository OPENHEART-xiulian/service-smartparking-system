package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.domain.query.SystemUsersQuery;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;

import java.util.List;

/**
 * 系统管理-用户表(SystemUsers)表服务接口
 *
 * @author zoey
 * @since 2024-08-13 15:25:12
 */
public interface SystemUsersService extends IService<SystemUsers> {

    /**
     * 系统管理-用户表-分页查询列表
     *
     * @param query
     * @return
     */
    PageInfoRespQuery getPageList(SystemUsersQuery query);

    /**
     * 查询用户信息
     *
     * @param account
     * @return
     */
    SystemUsers getEntityByAccountOrId(String account);

    /**
     * 用户账号密码登录
     *
     * @param query
     * @return
     */
    SystemUsers login(SystemUsersQuery query);

    /**
     * 更改密码，并返回更改密码后的token
     *
     * @return
     */
    SystemUsers updatePassword(SystemUsersQuery query);

    /**
     * 用户编辑-只更新头像和用户名
     *
     * @param user
     * @return
     */
    SystemUsers updateUser(SystemUsers user);

    /**
     * 用户新增或编辑
     *
     * @param user
     * @return
     */
    SystemUsers createOrUpdateUser(SystemUsers user);

    /**
     * 用户删除
     *
     * @param ids
     */
    void removeUser(List<String> ids);

    /**
     * 重置密码
     *
     * @param ids
     * @param password
     */
    void resettingPassword(List<String> ids, String password);

    /**
     * 更新用户状态
     *
     * @param ids
     * @param status
     */
    void updateStatus(List<String> ids, Integer status);

    /**
     * 查询用户角色ID为3 或者 4 的用户id，只取随机一条
     *
     * @return
     */
    String getEntityId();
}
