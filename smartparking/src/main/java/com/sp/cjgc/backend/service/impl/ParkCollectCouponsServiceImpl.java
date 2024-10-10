package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import com.sp.cjgc.backend.domain.RecordCarEnter;
import com.sp.cjgc.backend.domain.SystemParkingDetail;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.enums.RoleEnum;
import com.sp.cjgc.backend.mapper.ParkCollectCouponsMapper;
import com.sp.cjgc.backend.order.ChargeRulesUtil;
import com.sp.cjgc.backend.order.OrderUtils;
import com.sp.cjgc.backend.order.TemporaryUtil;
import com.sp.cjgc.backend.service.OrderPaidCatOutboundService;
import com.sp.cjgc.backend.service.ParkCollectCouponsService;
import com.sp.cjgc.backend.service.RecordCarEnterService;
import com.sp.cjgc.backend.service.SystemParkingDetailService;
import com.sp.cjgc.backend.service.SystemUsersService;
import com.sp.cjgc.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 商户-停车领劵(ParkCollectCoupons)表服务实现类
 *
 * @author zoey
 * @since 2024-08-22 09:56:37
 */
@Service
public class ParkCollectCouponsServiceImpl
        extends ServiceImpl<ParkCollectCouponsMapper, ParkCollectCoupons>
        implements ParkCollectCouponsService {

    @Autowired
    private SystemUsersService systemUsersService;
    @Autowired
    private RecordCarEnterService recordCarEnterService;
    @Autowired
    private SystemParkingDetailService parkingDetailService;
    @Autowired
    private OrderPaidCatOutboundService orderPaidCatOutboundService;

    /**
     * 用户领劵
     *
     * @param userId        商户ID
     * @param licensePlates 车牌号/手机号
     */
    @Override
    public synchronized ParkCollectCoupons userCollectCoupons(String userId, String licensePlates, Long timestamp) {
        // 数据验证
        assignedNumber(userId);
        // 获取用户信息
        SystemUsers users = systemUsersService.getEntityByAccountOrId(userId);
        if (null != timestamp && timestamp > 0) {
            LocalDateTime now = LocalDateTime.now();
            long timestamp1 = now.toInstant(ZoneOffset.UTC).toEpochMilli();
            // 判断时间戳是否有值
            if (timestamp < timestamp1) throw new BizException(RespEnum.OVERDUE_ERROR);
        }
        // 根据车牌号码查询车辆进场记录
        RecordCarEnter recordCarEnter = recordCarEnterService.getByPlates(null, licensePlates);
        if (Objects.isNull(recordCarEnter)) throw new BizException(RespEnum.NOT_COUPONS_ERROR_6);
        // 获取临保收费标准，单位：元/小时
        String tollStandardStr = ChargeRulesUtil.getTollStandard();
        // 判断是否存在临保收费标准
        if (Objects.isNull(tollStandardStr)) throw new BizException(RespEnum.NOT_ERROR_1);

        // 查询该车牌号是否已领取了优惠卷
        if (Objects.nonNull(this.getOne(
                new LambdaQueryWrapper<ParkCollectCoupons>()
                        .eq(ParkCollectCoupons::getMainlandLicensePlates, licensePlates)
                        .eq(ParkCollectCoupons::getIsItOver, 0)
        ))) throw new BizException(RespEnum.NOT_COUPONS_ERROR_5);

        // 获取最早的进场时间
        LocalDateTime startTime = recordCarEnter.getStartTime();
        // 获取系统当前时间
        LocalDateTime now = LocalDateTime.now();
        // 创建对象
        ParkCollectCoupons parkCollectCoupons = new ParkCollectCoupons();
        parkCollectCoupons.setUserId(userId);
        parkCollectCoupons.setMainlandLicensePlates(licensePlates);
        parkCollectCoupons.setStartTime(startTime);
        parkCollectCoupons.setEndTime(now);
        parkCollectCoupons.setReceiveTime(now);
        parkCollectCoupons.setIsItOver(0);
        // 计算停车时长
        parkCollectCoupons.setTotalDuration(TemporaryUtil.getParkingDuration(startTime, now));
        // 计算停车总金额、优惠金额、优惠时长、优惠信息并保存保存数据
        TemporaryUtil.computationalCost(parkCollectCoupons, users.getFreeTime(), tollStandardStr);

        // 获取需付金额
        String totalIncomeAmount = parkCollectCoupons.getTotalIncomeAmount();
        // 如果需付金额大于0，则生成支付订单
        if (totalIncomeAmount != null && Double.parseDouble(totalIncomeAmount) > 0) {
            // 生成支付订单
            orderPaidCatOutboundService.createOrder(OrderUtils.createOrder(parkCollectCoupons));
            parkCollectCoupons.setIsPlay(0);
        } else {
            parkCollectCoupons.setIsPlay(1);
        }
        this.save(parkCollectCoupons);
        return parkCollectCoupons;
    }

    /**
     * 获取商户可发放的车位数量
     *
     * @param userId
     * @return
     */
    @Override
    public synchronized Map<String, Object> assignedNumber(String userId) {
        // 获取免费停车时长
        SystemUsers users = systemUsersService.getEntityByAccountOrId(userId);
        // 判断是否存在商户信息
        if (Objects.isNull(users)) throw new BizException(RespEnum.NOT_FOUND_ERROR);
        // 判断商户的角色是否正确
        if (Objects.equals(users.getRoleId(), RoleEnum.CJ.getRoleCode())
                || Objects.equals(users.getRoleId(), RoleEnum.GL.getRoleCode())
        ) throw new BizException(RespEnum.ROLE_ERROR);
        if (users.getStatus() == 2) throw new BizException(RespEnum.USER_ERROR_1);
        if (users.getStatus() == 3) throw new BizException(RespEnum.USER_ERROR_2);
        // 使用mybatis的方法，根据user_id获取该商户的信息
        SystemParkingDetail parkingDetail = parkingDetailService.getOne(
                new LambdaQueryWrapper<SystemParkingDetail>().eq(SystemParkingDetail::getUserId, userId));
        // 判断该商户是否有租赁车位
        if (Objects.isNull(parkingDetail)) throw new BizException(RespEnum.NOT_COUPONS_ERROR_1);
        // 以当前系统时间为标准，判断该商户租赁的车位是否已经过期
        if (LocalDateTime.now().isAfter(parkingDetail.getEndTime()))
            throw new BizException(RespEnum.NOT_COUPONS_ERROR_2);
        if (!LocalDateTime.now().isAfter(parkingDetail.getStartTime()))
            throw new BizException(RespEnum.NOT_COUPONS_ERROR_3);
        // 获取租赁车位总数
        Integer assignedNumber = parkingDetail.getAssignedNumber();
        // 获取已发放车位数量
        Long countTotal = this.baseMapper.countTotal(users.getId());
        // 判断是否还有车位可发放
        if (countTotal > assignedNumber) throw new BizException(RespEnum.NOT_COUPONS_ERROR_4);
        // 创建返回值对象
        Map<String, Object> params = new HashMap<>();
        params.put(MyConstants.KEY_NAME_1, assignedNumber - countTotal.intValue());
        params.put(MyConstants.KEY_NAME_2, assignedNumber - countTotal.intValue() > 0 ? "1" : "0");
        params.put(MyConstants.KEY_NAME_3, users.getUserName());
        // 返回剩余可发放的车位数量
        return params;
    }

    /**
     * 根据车牌号码获取优惠卷信息
     *
     * @param licensePlates
     * @return
     */
    @Override
    public ParkCollectCoupons getEntityByLicensePlates(String licensePlates) {
        return this.baseMapper.selectByPrimaryKey(licensePlates);
    }
}
