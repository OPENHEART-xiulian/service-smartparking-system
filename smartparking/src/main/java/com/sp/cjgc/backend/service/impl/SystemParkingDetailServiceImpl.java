package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.SystemParking;
import com.sp.cjgc.backend.domain.SystemParkingDetail;
import com.sp.cjgc.backend.domain.query.SystemParkingDetailQuery;
import com.sp.cjgc.backend.mapper.SystemParkingDetailMapper;
import com.sp.cjgc.backend.service.SystemParkingDetailService;
import com.sp.cjgc.backend.service.SystemParkingService;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统管理-车位数量明细表(SystemParkingDetail)表服务实现类
 *
 * @author zoey
 * @since 2024-08-15 16:24:56
 */
@Service
public class SystemParkingDetailServiceImpl extends ServiceImpl<SystemParkingDetailMapper, SystemParkingDetail> implements SystemParkingDetailService {

    @Autowired
    private SystemParkingService systemParkingService;

    /**
     * 系统管理-车位数量明细表-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(SystemParkingDetailQuery query) {
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<SystemParkingDetail> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }

    /**
     * 统计已分配车位总数量
     *
     * @return
     */
    @Override
    public Integer sumAssignedNumber() {
        Integer assignedNumber = this.baseMapper.sumAssignedNumber();
        return null == assignedNumber ? 0 : assignedNumber;
    }

    /**
     * 新增或更新
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemParkingDetail createOrUpdate(SystemParkingDetail entity) {
        // 查询车位数量
        SystemParking parking = systemParkingService.getEntity();
        // 判断是否存在车位总数量
        if (Objects.isNull(parking))
            throw new BizException(RespEnum.FAILURE.getCode(), "请先配置车位总数量和可租赁车位数量");
        // 判断当前可租赁车位数量是否大于0
        if (parking.getParkingNumber() <= 0) throw new BizException(RespEnum.FAILURE.getCode(), "当前车位总数量为0");
        if (parking.getDisposableNumber() <= 0)
            throw new BizException(RespEnum.FAILURE.getCode(), "当前可租赁车位数量为0");
        // 根据用户ID查询数据是否存在
        SystemParkingDetail oldEntity = this.baseMapper.getEntityByUserId(entity.getUserId());
        // 查询已分配的车位总数量
        Integer assignedNumber = this.sumAssignedNumber();
        if (Objects.nonNull(oldEntity)) {
            entity.setId(oldEntity.getId());
            assignedNumber = assignedNumber - oldEntity.getAssignedNumber();
        }

        // 判断分配的车位数量 + 已分配的车位总数量是否大于可租赁车位总数量
        if (assignedNumber + entity.getAssignedNumber() > parking.getDisposableNumber())
            throw new BizException(RespEnum.FAILURE.getCode(), "当前可分配的车位数量不足，当前可分配车位数量不可大于【" + (parking.getDisposableNumber() - assignedNumber) + "】");
        // 判断是否存在ID
        this.saveOrUpdate(entity);
        return this.getById(entity.getId());
    }
}
