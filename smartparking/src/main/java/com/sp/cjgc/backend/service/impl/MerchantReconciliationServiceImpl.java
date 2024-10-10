package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.MerchantReconciliation;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import com.sp.cjgc.backend.domain.query.MerchantReconciliationQuery;
import com.sp.cjgc.backend.mapper.MerchantReconciliationMapper;
import com.sp.cjgc.backend.service.MerchantReconciliationService;
import com.sp.cjgc.backend.order.TemporaryUtil;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 商户-对账记录(MerchantReconciliation)表服务实现类
 *
 * @author zoey
 * @since 2024-08-20 13:13:44
 */
@Service
public class MerchantReconciliationServiceImpl
        extends ServiceImpl<MerchantReconciliationMapper, MerchantReconciliation>
        implements MerchantReconciliationService {

    /**
     * 商户-对账记录-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(MerchantReconciliationQuery query) {
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 判断是否存在查询条件
        if (Objects.isNull(query.getYearNumber())) {
            // 默认查询当年当月的
            query.setYearNumber(LocalDateTime.now().getYear() + "-" + LocalDateTime.now().getMonthValue());
        }
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<MerchantReconciliation> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }

    /**
     * 将状态更改为已发放
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String id) {
        // 根据ID查询数据是否存在
        MerchantReconciliation entity = this.getById(id);
        // 判断数据是否存在
        if (Objects.isNull(entity))
            throw new BizException(RespEnum.FAILURE.getCode(), "数据不存在");

        // 判断数据的状态是否是已发放状态
        if (MyConstants.GRANTED.equals(entity.getStatus()))
            throw new BizException(RespEnum.FAILURE.getCode(), "该商家已发放");
        entity.setStatus(MyConstants.GRANTED);
        this.updateById(entity);
    }

    /**
     * 创建或更新商家对账记录
     *
     * @param parkCollectCoupons
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdateEntity(ParkCollectCoupons parkCollectCoupons) {
        // 获取订单支付时间
        LocalDateTime payTime = parkCollectCoupons.getPlayTime();
        // 转为年月字符串
        String yearMonth = payTime.getYear() + "-" + payTime.getMonthValue();
        // 查询未对账的商家记录
        MerchantReconciliation oldEntity = this.baseMapper.getEntityByUserId(
                MyConstants.UNWARRANTED, parkCollectCoupons.getUserId(), yearMonth);
        // 解析停车时长字符串，并返回以小时为单位的总小时数。
        double toHours = TemporaryUtil.parseDurationToHours(parkCollectCoupons.getTotalDuration());
        // 判断是否存在数据
        if (Objects.nonNull(oldEntity)) {
            // 累计停车总时长
            oldEntity.setTotalDuration(
                    new BigDecimal(oldEntity.getTotalDuration())
                            .add(new BigDecimal(toHours)).toString()
            );
            // 累计总计金额
            oldEntity.setTotalAmount(
                    new BigDecimal(oldEntity.getTotalAmount())
                            .add(new BigDecimal(parkCollectCoupons.getTotalAmount())).toString()
            );
            // 累计总计优惠金额
            oldEntity.setTotalDiscountAmount(
                    new BigDecimal(oldEntity.getTotalDiscountAmount())
                            .add(new BigDecimal(parkCollectCoupons.getTotalDiscountAmount())).toString()
            );
            // 累计总收入金额
            oldEntity.setTotalIncomeAmount(
                    new BigDecimal(oldEntity.getTotalIncomeAmount())
                            .add(new BigDecimal(parkCollectCoupons.getTotalIncomeAmount())).toString()
            );
        } else {
            oldEntity = new MerchantReconciliation();
            oldEntity.setUserId(parkCollectCoupons.getUserId());
            oldEntity.setYearNumber(yearMonth);
            oldEntity.setMonthNumber(String.valueOf(payTime.getMonthValue()));
            oldEntity.setTotalDuration(String.valueOf(toHours));
            oldEntity.setTotalAmount(parkCollectCoupons.getTotalAmount());
            oldEntity.setTotalDiscountAmount(parkCollectCoupons.getTotalDiscountAmount());
            oldEntity.setTotalIncomeAmount(parkCollectCoupons.getTotalIncomeAmount());
            oldEntity.setStatus(MyConstants.UNWARRANTED);
        }
        this.saveOrUpdate(oldEntity);
    }
}
