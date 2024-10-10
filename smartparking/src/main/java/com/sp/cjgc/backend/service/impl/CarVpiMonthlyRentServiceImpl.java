package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.CarVisitor;
import com.sp.cjgc.backend.domain.CarVpiMonthlyRent;
import com.sp.cjgc.backend.domain.query.CarVpiMonthlyRentQuery;
import com.sp.cjgc.backend.mapper.CarVpiMonthlyRentMapper;
import com.sp.cjgc.backend.service.CarVpiMonthlyRentService;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 车辆管理-vip月租车登记管理表(CarVpiMonthlyRent)表服务实现类
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
@Service
public class CarVpiMonthlyRentServiceImpl extends ServiceImpl<CarVpiMonthlyRentMapper, CarVpiMonthlyRent> implements CarVpiMonthlyRentService {

    /**
     * 车辆管理-vip月租车登记管理表-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(CarVpiMonthlyRentQuery query) {
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<CarVpiMonthlyRent> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }

    /**
     * 根据内地车牌查询是否存在VIP月租车
     *
     * @param mainlandLicensePlates
     */
    @Override
    public CarVpiMonthlyRent getByLicensePlates(String mainlandLicensePlates) {
        // 根据车牌号码 查询数据是否存在
        return this.baseMapper.getByLicensePlates(mainlandLicensePlates);
    }

    /**
     * 车辆管理-vip月租车登记管理表-新增或修改
     *
     * @param entity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdate(CarVpiMonthlyRent entity) {
        // 获取系统当前时间
        LocalDateTime now = LocalDateTime.now();
        // 判断是否是新增
        if (StringUtils.isBlank(entity.getId())) {
            // 验证车牌号是否唯一
            dataValidation(entity.getMainlandLicensePlates());
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
        } else {
            // 根据ID查询数据
            CarVpiMonthlyRent oldEntity = this.getById(entity.getId());
            // 判断车牌号是否发生改变
            if (!entity.getMainlandLicensePlates().equals(oldEntity.getMainlandLicensePlates())) {
                // 验证车牌号是否唯一
                dataValidation(entity.getMainlandLicensePlates());
            }
            entity.setUpdateTime(now);
        }
        this.saveOrUpdate(entity);
    }

    /**
     * 验证该内部月租车是否已经重复录入
     *
     * @param mainlandLicensePlates
     */
    private void dataValidation(String mainlandLicensePlates) {
        CarVpiMonthlyRent visitor = this.getByLicensePlates(mainlandLicensePlates);
        if (Objects.nonNull(visitor))
            throw new BizException(RespEnum.FAILURE.getCode(), "车牌号码为【" + mainlandLicensePlates + "】的内部月租车已登记。");
    }
}
