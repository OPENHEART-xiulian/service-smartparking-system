package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.backend.domain.MonthlyStatistics;
import com.sp.cjgc.backend.domain.OrderPaidCatOutbound;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import com.sp.cjgc.backend.domain.RecordCarEnter;
import com.sp.cjgc.backend.domain.RevenueStatistics;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.domain.query.OrderPaidCatOutboundQuery;
import com.sp.cjgc.backend.enums.CarGroupEnum;
import com.sp.cjgc.backend.enums.OrderStatus;
import com.sp.cjgc.backend.enums.RoleEnum;
import com.sp.cjgc.backend.mapper.OrderPaidCatOutboundMapper;
import com.sp.cjgc.backend.order.ExitParkingUtil;
import com.sp.cjgc.backend.service.MerchantReconciliationService;
import com.sp.cjgc.backend.service.OrderPaidCatOutboundService;
import com.sp.cjgc.backend.service.ParkCollectCouponsService;
import com.sp.cjgc.backend.service.RecordCarEnterService;
import com.sp.cjgc.backend.service.SystemCameraDeviceService;
import com.sp.cjgc.backend.service.SystemUsersService;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import com.sp.cjgc.hik.utils.GateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 订单-车辆出库已支付订单记录(OrderPaidCatOutbound)表服务实现类
 *
 * @author zoey
 * @since 2024-08-20 13:50:20
 */
@Slf4j
@Service
public class OrderPaidCatOutboundServiceImpl
        extends ServiceImpl<OrderPaidCatOutboundMapper, OrderPaidCatOutbound>
        implements OrderPaidCatOutboundService {

    // 是否开启控制闸 true|开启  false｜关闭
    @Value("${global.controlGate}")
    private boolean controlGate;

    @Autowired
    private SystemUsersService systemUsersService;
    @Autowired
    private SystemCameraDeviceService systemCameraDeviceService;
    @Lazy
    @Autowired
    private ParkCollectCouponsService parkCollectCouponsService;
    @Autowired
    private RecordCarEnterService recordCarEnterService;
    @Autowired
    private MerchantReconciliationService merchantReconciliationService;

    /**
     * 订单-车辆出库已支付订单记录-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(OrderPaidCatOutboundQuery query) {
        // 判断是否存在用户ID
        if (StringUtils.isNotBlank(query.getUserId())) {
            // 查询用户信息
            SystemUsers users = systemUsersService.getEntityByAccountOrId(query.getUserId());
            // 判断用户角色是否是非管理员
            if (RoleEnum.GL.getRoleId().equals(users.getRoleId())
                    || RoleEnum.CJ.getRoleId().equals(users.getRoleId())
            ) {
                // 设置用户ID
                query.setUserId("");
            }
        }
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<OrderPaidCatOutbound> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }

    /**
     * 根据车牌号查询当天支付时间为最新的提前支付订单
     *
     * @param playStatus 支付状态
     * @param plates     车牌号
     * @return
     */
    @Override
    public OrderPaidCatOutbound getEntityByPlates(String playStatus, String plates) {
        return this.baseMapper.getEntityByPlates(playStatus, plates);
    }

    /**
     * 根据车牌号查询当天支付时间为最新的支付订单
     *
     * @param plates 车牌号
     * @return
     */
    @Override
    public OrderPaidCatOutbound getEntityBy(String plates, String deviceIp) {
        return this.baseMapper.getEntity(OrderStatus.NOTPAY.getType(), plates, deviceIp);
    }

    /**
     * 车辆在出库的时候查询预支付订单
     * 或提前支付的的时候生成预支付订单
     * 当有出库设备IP的时候，就是查询预支付订单
     * 当有车牌号或手机号码的时候，就是生成预支付订单
     *
     * @param license  车牌号或手机号码
     * @param deviceIp 出库设备IP
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized OrderPaidCatOutbound getEntity(String license, String deviceIp) {
        // 查询或创建订单
        OrderPaidCatOutbound order = ExitParkingUtil.createOrder(license, deviceIp);
        if (Objects.isNull(order)) return new OrderPaidCatOutbound();
        if (StringUtils.isNotBlank(deviceIp)) {
            order.setDeviceIp(deviceIp);
            // 判断是否需要支付
            if (Double.parseDouble(order.getTotalIncomeAmount()) <= 0) {
                // 开启闸机、更新出库记录
                controlGate(deviceIp, order.getOrderNumber(), order.getMainlandLicensePlates());
                // 支付金额为0，直接放行，更改订单为支付成功状态
                order.setPlayStatus(OrderStatus.SUCCESS.getType());
                order.setPlayTime(LocalDateTime.now());
                return order;
            }
        } else {
            // 没有出库设备IP就是提前支付
            order.setAdvancePayment(1);
        }
        this.saveOrUpdate(order);
        return order;
    }

    /**
     * 无牌车出场生成预支付订单
     *
     * @param phoneNumber 手机号码
     * @param deviceIp    出口设备IP
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized OrderPaidCatOutbound createOrder(String phoneNumber, String deviceIp) {
        // 获取设备信息
        SystemCameraDevice cameraDevice = systemCameraDeviceService.getEntity(deviceIp, null);
        // 查询用户是否存在未支付订单
        OrderPaidCatOutbound order = ExitParkingUtil.createOrderByWuChePai(phoneNumber, deviceIp);
        order.setAdvancePayment(0);
        order.setDeviceIp(deviceIp);
        order.setTypeCode(CarGroupEnum.WU_PAI_CHE.getCarGroupId().toString());
        order.setTypeName(CarGroupEnum.WU_PAI_CHE.getCarGroupName());
        // 判断是否需要支付
        if (Double.parseDouble(order.getTotalIncomeAmount()) <= 0) {
            // 开启闸机
            GateUtil.controlGate(controlGate, MyConstants.ZJ_OPEN, cameraDevice);
            // 支付金额为0，直接放行，更改订单为支付成功状态
            order.setPlayStatus(OrderStatus.SUCCESS.getType());
            order.setPlayTime(LocalDateTime.now());
        } else {
            this.saveOrUpdate(order);
        }
        // 更新进场记录状态
        RecordCarEnter recordCarEnter = recordCarEnterService.getByPlates(cameraDevice.getGroupId(), order.getMainlandLicensePlates());
        if (Objects.nonNull(recordCarEnter)) {
            recordCarEnter.setStatus(1);
            recordCarEnterService.updateById(recordCarEnter);
            // 创建出场记录
            ExitParkingUtil.createRecordsCarOut(order.getEndTime(), recordCarEnter, cameraDevice);
            log.info("手机号码【{}】创建出场纪录成功", order.getMainlandLicensePlates());
        }
        return order;
    }

    /**
     * 订单统计
     *
     * @param year
     * @param userId
     * @return
     */
    @Override
    public List<MonthlyStatistics> getMonthlyStatistics(String year, String userId) {
        // 判断是否有年份值，如果没有，则默认查当前年
        if (StringUtils.isBlank(year)) year = LocalDateTime.now().getYear() + "";
        // 判断是否存在用户ID
        if (StringUtils.isNotBlank(userId)) {
            // 查询用户信息
            SystemUsers users = systemUsersService.getEntityByAccountOrId(userId);
            // 判断用户角色是否是非管理员
            if (RoleEnum.GL.getRoleId().equals(users.getRoleId())
                    || RoleEnum.CJ.getRoleId().equals(users.getRoleId())
            ) userId = "";
        }
        return this.baseMapper.getMonthlyStatistics(year, userId);
    }

    /**
     * 临保收益统计
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
            ) userId = "";
        }
        // 今日临时金额
        statistics.setTodayTemporaryAmount(
                this.baseMapper.getTodayPayments(OrderStatus.SUCCESS.getType(), userId) == null
                        ? 0.0
                        : this.baseMapper.getTodayPayments(OrderStatus.SUCCESS.getType(), userId)
        );
        // 统计总临时金额
        statistics.setTotalTemporaryAmount(
                this.baseMapper.getTotalPayments(OrderStatus.SUCCESS.getType(), userId) == null
                        ? 0.0
                        : this.baseMapper.getTotalPayments(OrderStatus.SUCCESS.getType(), userId)
        );
        return statistics;
    }


    /**
     * 创建订单
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized OrderPaidCatOutbound createOrder(OrderPaidCatOutbound entity) {
        //查找已存在但未支付的订单
        OrderPaidCatOutbound orderInfo = getNoPayOrderByProductId(entity.getUserId(), entity.getMainlandLicensePlates());
        // 判断订单是否存在
        if (Objects.nonNull(orderInfo)) return orderInfo;
        this.saveOrUpdate(entity);
        return entity;
    }

    /**
     * 根据商户id和车牌号查询未支付订单
     * 防止重复创建订单对象
     *
     * @param userId 商户ID
     * @param plates 车牌号或手机号
     * @return
     */
    private OrderPaidCatOutbound getNoPayOrderByProductId(String userId, String plates) {
        LambdaQueryWrapper<OrderPaidCatOutbound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPaidCatOutbound::getUserId, userId);
        queryWrapper.eq(OrderPaidCatOutbound::getPlayStatus, OrderStatus.NOTPAY.getType());
        queryWrapper.eq(OrderPaidCatOutbound::getMainlandLicensePlates, plates);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据订单号获取订单状态
     *
     * @param orderNo
     * @return
     */
    private String getOrderStatus(String orderNo) {
        LambdaQueryWrapper<OrderPaidCatOutbound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPaidCatOutbound::getOrderNumber, orderNo);
        OrderPaidCatOutbound orderInfo = baseMapper.selectOne(queryWrapper);
        if (Objects.isNull(orderInfo)) return null;
        return orderInfo.getPlayStatus();
    }

    /**
     * 根据订单号更新订单状态
     *
     * @param orderNo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusByOrderNo(String orderNo) {
        LambdaQueryWrapper<OrderPaidCatOutbound> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPaidCatOutbound::getOrderNumber, orderNo);
        queryWrapper.eq(OrderPaidCatOutbound::getPlayStatus, OrderStatus.NOTPAY.getType());
        OrderPaidCatOutbound orderInfo = baseMapper.selectOne(queryWrapper);

        // 判断数据是否存在
        if (Objects.nonNull(orderInfo)) {
            // 获取设备IP
            String deviceIp = orderInfo.getDeviceIp();
            // 如果不存在设备IP，就意味着是提前支付
            if (StringUtils.isBlank(deviceIp)) orderInfo.setAdvancePayment(1);
            // 查询用户是否有领劵
            ParkCollectCoupons parkCollectCoupons = parkCollectCouponsService.getEntityByLicensePlates(orderInfo.getMainlandLicensePlates());
            if (Objects.nonNull(parkCollectCoupons)) {
                parkCollectCoupons.setIsPlay(1);
                parkCollectCoupons.setPlayTime(LocalDateTime.now());
                parkCollectCouponsService.updateById(parkCollectCoupons);

                // 创建商家对账记录
                merchantReconciliationService.createOrUpdateEntity(parkCollectCoupons);
            }

            // 判断是否是在出口出支付，如果是在出口出支付，就控制闸机开启
            if (StringUtils.isNotBlank(deviceIp)) {
                // 开启闸机、更新出库记录
                controlGate(deviceIp, orderNo, orderInfo.getMainlandLicensePlates());
            }
            // 更新订单状态
            orderInfo.setPlayStatus(OrderStatus.SUCCESS.getType());
            orderInfo.setPlayTime(LocalDateTime.now());
            this.updateById(orderInfo);
        }
    }

    /**
     * 开启闸机、更新出库记录
     *
     * @param deviceIp
     * @param orderNo
     * @param plates
     */
    private void controlGate(String deviceIp, String orderNo, String plates) {
        // 获取设备信息
        SystemCameraDevice cameraDevice = systemCameraDeviceService.getEntity(deviceIp, null);
        // 开启闸机
        GateUtil.controlGate(controlGate, MyConstants.ZJ_OPEN, cameraDevice);
        log.info("订单号：{}，设备IP：{}，在{}出口出支付，控制闸机开启", orderNo, deviceIp, cameraDevice.getDeviceLocation());
        // 更新进场记录状态
        RecordCarEnter recordCarEnter = recordCarEnterService.getByPlates(cameraDevice.getGroupId(), plates);
        if (Objects.nonNull(recordCarEnter)) {
            recordCarEnter.setStatus(1);
            recordCarEnterService.updateById(recordCarEnter);
        }
    }
}
