package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.SystemRole;
import com.sp.cjgc.backend.domain.SystemUserRole;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.domain.query.SystemUsersQuery;
import com.sp.cjgc.backend.mapper.SystemUsersMapper;
import com.sp.cjgc.backend.service.SystemRoleService;
import com.sp.cjgc.backend.service.SystemUserRoleService;
import com.sp.cjgc.backend.service.SystemUsersService;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import com.sp.cjgc.common.utils.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 系统管理-用户表(SystemUsers)表服务实现类
 *
 * @author zoey
 * @since 2024-08-13 15:25:13
 */
@Service
public class SystemUsersServiceImpl extends ServiceImpl<SystemUsersMapper, SystemUsers> implements SystemUsersService {

    @Autowired
    private SystemRoleService systemRoleService;
    @Autowired
    private SystemUserRoleService systemUserRoleService;

    /**
     * 系统管理-用户表-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(SystemUsersQuery query) {
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<SystemUsers> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }

    /**
     * 查询用户信息
     *
     * @param account
     * @return
     */
    @Override
    public SystemUsers getEntityByAccountOrId(String account) {
        return this.baseMapper.getEntityByAccount(account);
    }

    /**
     * 用户账号密码登录
     *
     * @param query
     * @return
     */
    @Override
    public SystemUsers login(SystemUsersQuery query) {
        // 获取账号
        String account = query.getAccount();
        if (StringUtils.isBlank(account)) throw new BizException(RespEnum.FAILURE.getCode(), "请输入账号");
        // 获取密码
        String password = query.getPassword();
        if (StringUtils.isBlank(password)) throw new BizException(RespEnum.FAILURE.getCode(), "请输入密码");
        // 根据账号查询用户信息
        SystemUsers oldUser = this.baseMapper.getEntityByAccount(account);
        if (Objects.isNull(oldUser)) throw new BizException(RespEnum.FAILURE.getCode(), "用户不存在");
        if (oldUser.getStatus() == 2)
            throw new BizException(RespEnum.FAILURE.getCode(), "用户【" + account + "】已被锁定");
        if (oldUser.getStatus() == 3)
            throw new BizException(RespEnum.FAILURE.getCode(), "用户【" + account + "】已被禁用");
        synchronized (this) {
            // 密码匹配
            if (DigestUtils.md5Hex(password).equals(oldUser.getPassword())) {
                // 生成token
                oldUser.setToken(JwtUtil.sign(oldUser.getAccount(), oldUser.getPassword()));
                return oldUser;
            } else {
                throw new BizException(RespEnum.FAILURE.getCode(), "用户【" + account + "】密码错误");
            }
        }
    }

    /**
     * 更改密码，并返回更改密码后的token
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemUsers updatePassword(SystemUsersQuery query) {
        // 获取账号
        String account = query.getAccount();
        if (StringUtils.isBlank(account)) throw new BizException(RespEnum.FAILURE.getCode(), "请输入账号");
        // 获取原密码
        String oldPassword = query.getOldPassword();
        if (StringUtils.isBlank(oldPassword)) throw new BizException(RespEnum.FAILURE.getCode(), "请输入原密码");
        // 获取密码
        String password = query.getPassword();
        if (StringUtils.isBlank(password)) throw new BizException(RespEnum.FAILURE.getCode(), "请输入密码");
        // 根据账号查询用户信息
        SystemUsers oldUser = this.baseMapper.getEntityByAccount(account);
        if (Objects.isNull(oldUser)) throw new BizException(RespEnum.FAILURE.getCode(), "用户不存在");
        // 密码加密
        oldPassword = DigestUtils.md5Hex(oldPassword);
        // 判断原密码是否一致
        if (!oldPassword.equals(oldUser.getPassword())) throw new BizException(RespEnum.PASSWORD_ERROR);
        // 更新密码
        oldUser.setPassword(DigestUtils.md5Hex(password));
        // 更新数据
        this.updateById(oldUser);
        // 生成token
        oldUser.setToken(JwtUtil.sign(oldUser.getAccount(), oldUser.getPassword()));
        return oldUser;
    }

    /**
     * 用户编辑-只更新头像和用户名
     *
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemUsers updateUser(SystemUsers user) {
        // 根据ID获取用户信息
        SystemUsers openUser = this.getById(user.getId());
        if (Objects.isNull(openUser)) throw new BizException(RespEnum.FAILURE.getCode(), "用户不存在");
        // 根据账号查询用户信息
        SystemUsers oldUser = this.baseMapper.getEntityByAccount(openUser.getAccount());
        // 更新数据
        if (StringUtils.isNotBlank(user.getHeadSculpture())) {
            oldUser.setHeadSculpture(user.getHeadSculpture());
        }
        if (StringUtils.isNotBlank(user.getUserName())) {
            oldUser.setUserName(user.getUserName());
        }
        synchronized (this) {
            oldUser.setCreateTime(LocalDateTime.now());
            // 更新用户信息
            this.updateById(oldUser);
        }
        // 生成token
        oldUser.setToken(JwtUtil.sign(oldUser.getAccount(), oldUser.getPassword()));
        return oldUser;
    }

    /**
     * 用户新增或编辑
     *
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized SystemUsers createOrUpdateUser(SystemUsers user) {
        user.setCreateTime(LocalDateTime.now());
        // 判断是否存在用户ID
        if (StringUtils.isNotBlank(user.getId())) {
            // 更新
            return updateUserAll(user);
        } else {
            // 新增
            return createUser(user);
        }
    }

    /**
     * 用户新增
     *
     * @param user
     * @return
     */
    public SystemUsers createUser(SystemUsers user) {
        // 根据账号查询用户信息
        SystemUsers oldUser = this.baseMapper.getEntityByAccount(user.getAccount());
        // 判断账号是否已存在
        if (Objects.nonNull(oldUser)) throw new BizException(RespEnum.FAILURE.getCode(), "账号已存在");
        // 判断是否存在角色ID
        if (StringUtils.isBlank(user.getRoleId())) throw new BizException(RespEnum.FAILURE.getCode(), "请选择角色");
        // 查询角色是否存在
        SystemRole role = systemRoleService.getById(user.getRoleId());
        if (Objects.isNull(role)) throw new BizException(RespEnum.FAILURE.getCode(), "请选择正确角色");
        // 判断角色是否是超级管理员
        if (!user.getRoleId().equals("4") && !user.getRoleId().equals("3")) {
            if (null == user.getFreeTime() || user.getFreeTime() <= 0) {
                throw new BizException(RespEnum.FAILURE.getCode(), "请输入合适的免费停车时长");
            }
        }
        // 密码加密
        user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex("123456")));
        // 默认状态为正常
        if (null == user.getStatus() || user.getStatus() <= 0) user.setStatus(1);
        // 创建用户
        this.save(user);
        // 添加用户角色
        createRole(user);
        return this.baseMapper.getEntityByAccount(user.getAccount());
    }

    /**
     * 用户编辑-可更新全字段
     *
     * @param user
     * @return
     */
    public SystemUsers updateUserAll(SystemUsers user) {
        // 根据ID获取用户信息
        SystemUsers openUser = this.baseMapper.getEntityByUserId(user.getId());
        if (Objects.isNull(openUser)) throw new BizException(RespEnum.FAILURE.getCode(), "用户不存在");
        // 判断是否允许更改
        if (openUser.getRoleId().equals("4"))
            throw new BizException(RespEnum.FAILURE.getCode(), "超级管理员账号，不可更改");
        // 判断是否有更新账号
        if (StringUtils.isNotBlank(user.getAccount()) && !openUser.getAccount().equals(user.getAccount())) {
            // 根据账号查询用户信息
            SystemUsers oldUser = this.baseMapper.getEntityByAccount(user.getAccount());
            if (Objects.nonNull(oldUser)) throw new BizException(RespEnum.FAILURE.getCode(), "账号已存在");
            openUser.setAccount(user.getAccount());
        }
        // 判断是否存在角色ID
        if (StringUtils.isNotBlank(user.getRoleId())) {
            // 查询角色是否存在
            SystemRole role = systemRoleService.getById(user.getRoleId());
            if (Objects.isNull(role)) throw new BizException(RespEnum.FAILURE.getCode(), "请选择正确角色");
            openUser.setRoleId(user.getRoleId());
        }
        if (StringUtils.isNotBlank(user.getUserName())) {
            openUser.setUserName(user.getUserName());
        }
        if (StringUtils.isNotBlank(user.getHeadSculpture())) {
            openUser.setHeadSculpture(user.getHeadSculpture());
        }
        if (!user.getRoleId().equals("4") && !user.getRoleId().equals("3")) {
            if (null != user.getFreeTime() && user.getFreeTime() > 0) {
                openUser.setFreeTime(user.getFreeTime());
            } else {
                throw new BizException(RespEnum.FAILURE.getCode(), "请输入合适的免费停车时长");
            }
        }
        if (StringUtils.isNotBlank(user.getPhoneNumber())) {
            openUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (StringUtils.isNotBlank(user.getFrontBusinessLicense())) {
            openUser.setFrontBusinessLicense(user.getFrontBusinessLicense());
        }
        if (StringUtils.isNotBlank(user.getOppositeBusinessLicense())) {
            openUser.setOppositeBusinessLicense(user.getOppositeBusinessLicense());
        }
        if (null != user.getStatus() && user.getStatus() > 0) {
            openUser.setStatus(user.getStatus());
        } else {
            throw new BizException(RespEnum.FAILURE.getCode(), "请选合适的状态");
        }
        // 创建用户
        this.updateById(openUser);
        // 添加用户角色
        createRole(openUser);
        return openUser;
    }

    /**
     * 用户删除
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(List<String> ids) {
        // 遍历ID集合
        ids.forEach(id -> {
            // 根据ID获取用户信息
            SystemUsers openUser = this.baseMapper.getEntityByUserId(id);
            // 判断是否允许更改
            if (openUser.getRoleId().equals("4"))
                throw new BizException(RespEnum.FAILURE.getCode(), "存在超级管理员账号，不可删除");
        });
        // 先删除角色
        systemUserRoleService.remove(new QueryWrapper<SystemUserRole>().in("user_id", ids));
        this.removeByIds(ids);
    }

    /**
     * 重置密码
     *
     * @param ids
     * @param password
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resettingPassword(List<String> ids, String password) {
        if (StringUtils.isBlank(password)) throw new BizException(RespEnum.FAILURE.getCode(), "请输入密码");
        if (null == ids || ids.isEmpty()) throw new BizException(RespEnum.FAILURE.getCode(), "请选择用户");
        // 查询用户列表
        List<SystemUsers> users = this.baseMapper.selectList(new QueryWrapper<SystemUsers>().in("id", ids));
        if (users.isEmpty()) throw new BizException(RespEnum.FAILURE.getCode(), "用户不存在");
        // 密码加密
        String finalPassword = DigestUtils.md5Hex(password);
        // 遍历用户列表，更新密码
        users.forEach(user -> {
            user.setPassword(finalPassword);
        });
        this.updateBatchById(users);
    }

    /**
     * 更新用户状态
     *
     * @param ids
     * @param status
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(List<String> ids, Integer status) {
        // 遍历ID集合
        ids.forEach(id -> {
            // 根据ID获取用户信息
            SystemUsers openUser = this.baseMapper.getEntityByUserId(id);
            // 判断是否允许更改
            if (openUser.getRoleId().equals("4") && status != 1)
                throw new BizException(RespEnum.FAILURE.getCode(), "超级管理员账号不可" + (status == 2 ? "锁定" : "禁用"));
        });
        // 根据用户ID列表批量更新状态
        this.update(new UpdateWrapper<SystemUsers>().in("id", ids).set("status", status));
    }

    /**
     * 添加用户角色
     *
     * @param user
     */
    private void createRole(SystemUsers user) {
        // 创建用户角色
        SystemUserRole roleUser = new SystemUserRole();
        roleUser.setUserId(user.getId());
        roleUser.setRoleId(user.getRoleId());
        roleUser.setCreateTime(LocalDateTime.now());
        // 先删除
        systemUserRoleService.remove(new QueryWrapper<SystemUserRole>().eq("user_id", user.getId()));
        systemUserRoleService.save(roleUser);
    }

    /**
     * 查询用户角色ID为3 或者 4 的用户id，只取随机一条
     *
     * @return
     */
    @Override
    public String getEntityId() {
        return this.baseMapper.getEntity() == null ? MyConstants.ADMIN_USER_ID : this.baseMapper.getEntity().getId();
    }
}
