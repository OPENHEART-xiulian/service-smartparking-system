package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.backend.domain.MonthlyInsurancePayment;
import com.sp.cjgc.backend.domain.RevenueStatistics;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.domain.query.MonthlyInsurancePaymentQuery;
import com.sp.cjgc.backend.enums.RoleEnum;
import com.sp.cjgc.backend.mapper.MonthlyInsurancePaymentMapper;
import com.sp.cjgc.backend.service.MonthlyInsurancePaymentService;
import com.sp.cjgc.backend.service.SystemUsersService;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 月保-缴费记录(MonthlyInsurancePayment)表服务实现类
 *
 * @author zoey
 * @since 2024-08-16 13:23:23
 */
@Service
public class MonthlyInsurancePaymentServiceImpl extends ServiceImpl<MonthlyInsurancePaymentMapper, MonthlyInsurancePayment> implements MonthlyInsurancePaymentService {

    @Autowired
    private SystemUsersService systemUsersService;

    /**
     * 月保-缴费记录-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(MonthlyInsurancePaymentQuery query) {
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<MonthlyInsurancePayment> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }

    /**
     * 查询车辆月保记录
     *
     * @param plates
     * @return
     */
    @Override
    public MonthlyInsurancePayment getEntity(String plates) {
        return this.baseMapper.getCarStatus(null, plates);
    }

    /**
     * 查询车辆月保状态
     *
     * @param plates
     * @return false：过保，true：在保
     */
    @Override
    public boolean getCarStatus(String plates) {
        MonthlyInsurancePayment carStatus = this.baseMapper.getCarStatus(1, plates);
        if (carStatus == null) return false;
        return "1".equals(carStatus.getMonthlyStatus());
    }

    /**
     * 新增月保记录
     *
     * @param entity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdate(MonthlyInsurancePayment entity) {
        // 根据车牌号查询数据是否存在
        MonthlyInsurancePayment oldEntity = this.baseMapper.getCarStatus(null, entity.getMainlandLicensePlates());
        // 判断数据是否存在，且是否在保
        if (Objects.nonNull(oldEntity) && oldEntity.getPaymentStatus() == 1) {
            entity.setId(oldEntity.getId());
            entity.setAccumulatePaymentAmount(oldEntity.getAccumulatePaymentAmount() + entity.getPaymentAmount());
        }
        entity.setCreateTime(LocalDateTime.now());
        this.saveOrUpdate(entity);
    }

    /**
     * 月保收益统计
     *
     * @param userId
     * @return
     */
    @Override
    public RevenueStatistics getStatistics(String userId) {
        // 创建对象
        RevenueStatistics statistics = new RevenueStatistics();
        // 判断是否存在用户ID
        if (StringUtils.isNotBlank(userId)) {
            // 查询用户信息
            SystemUsers users = systemUsersService.getEntityByAccountOrId(userId);
            // 判断用户角色是否是非管理员
            if (RoleEnum.GL.getRoleId().equals(users.getRoleId())
                    || RoleEnum.CJ.getRoleId().equals(users.getRoleId())
            ) {
                statistics.setTodayPaymentAmount(this.baseMapper.getTodayPayments() == null ? 0.0 : this.baseMapper.getTodayPayments());
                statistics.setTotalAccumulatePaymentAmount(this.baseMapper.getTotalPayments() == null ? 0.0 : this.baseMapper.getTotalPayments());
            }
        }
        return statistics;
    }
}
