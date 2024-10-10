package com.sp.cjgc.backend.order;

import com.sp.cjgc.MyConstants;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.CarVisitor;
import com.sp.cjgc.backend.domain.MonthlyInsurancePayment;
import com.sp.cjgc.backend.domain.OrderPaidCatOutbound;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import com.sp.cjgc.backend.domain.RecordCarEnter;
import com.sp.cjgc.backend.domain.RecordsCarOutbound;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.enums.CarGroupEnum;
import com.sp.cjgc.backend.enums.OrderStatus;
import com.sp.cjgc.backend.service.CarVisitorService;
import com.sp.cjgc.backend.service.MonthlyInsurancePaymentService;
import com.sp.cjgc.backend.service.OrderPaidCatOutboundService;
import com.sp.cjgc.backend.service.ParkCollectCouponsService;
import com.sp.cjgc.backend.service.RecordCarEnterService;
import com.sp.cjgc.backend.service.RecordsCarOutboundService;
import com.sp.cjgc.backend.utils.DateTimeUtil;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.hik.utils.GateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: Zoey
 * @Since: 2024-09-02 13:11:41
 * @Description: 车辆出场记录工具类
 */
@Slf4j
@Component
public class ExitParkingUtil {

    // 是否开启控制闸 true|开启  false｜关闭
    @Value("${global.controlGate}")
    private boolean controlGate;
    @Autowired
    private CarVisitorService carVisitorService;
    @Autowired
    private RecordCarEnterService recordCarEnterService;
    @Autowired
    private ParkCollectCouponsService parkCollectCouponsService;
    @Autowired
    private RecordsCarOutboundService recordsCarOutboundService;
    @Autowired
    private OrderPaidCatOutboundService orderPaidCatOutboundService;
    @Autowired
    private MonthlyInsurancePaymentService monthlyInsurancePaymentService;

    private static boolean staticControlGate;
    private static CarVisitorService staticCarVisitorService;
    private static RecordCarEnterService staticRecordCarEnterService;
    private static ParkCollectCouponsService staticParkCollectCouponsService;
    private static RecordsCarOutboundService staticRecordsCarOutboundService;
    private static OrderPaidCatOutboundService staticOrderPaidCatOutboundService;
    private static MonthlyInsurancePaymentService staticMonthlyInsurancePaymentService;

    @PostConstruct
    public void init() {
        staticControlGate = this.controlGate;
        staticCarVisitorService = this.carVisitorService;
        staticRecordCarEnterService = recordCarEnterService;
        staticParkCollectCouponsService = this.parkCollectCouponsService;
        staticRecordsCarOutboundService = this.recordsCarOutboundService;
        staticOrderPaidCatOutboundService = this.orderPaidCatOutboundService;
        staticMonthlyInsurancePaymentService = this.monthlyInsurancePaymentService;
    }

    /**
     * 根据摄像头抓拍信息创建车辆出场记录
     *
     * @param license      车牌号
     * @param cameraDevice 摄像头设备信息
     */
    public static void createRecordsCarOut(String license, SystemCameraDevice cameraDevice) {
        // 判断是否有车牌号，且是否是开始计费进口
        if (CarGroupEnum.WU_PAI_CHE.getCarGroupName().equals(license) && cameraDevice.getIsToll() == 1) {
            log.info("无车牌号，不自动创建出场记录,请扫码出场");
            // 不开闸
            GateUtil.controlGate(staticControlGate, MyConstants.ZJ_CLOSE, cameraDevice);
            return;
        }
        // 查询车辆进场记录
        RecordCarEnter recordCarEnter = staticRecordCarEnterService.getByPlates(cameraDevice.getGroupId(), license);
        // 判断是否存在进场记录
        if (Objects.isNull(recordCarEnter))
            throw new BizException(RespEnum.FAILURE.getCode(), "车牌号为：【" + license + "】暂无进场记录。");

        // 获取系统当前时间
        LocalDateTime nowTime = DateTimeUtil.getNowTime();
        // 将当前时间格式化 yyyy-MM-dd HH:mm:ss
        String nowTimeStr = DateTimeUtil.getNowTimeStr(nowTime);

        // 定义表示，用于标记是否需要生成支付订单 true|需要 false|不需要
        boolean flg = false;
        // 判断此处是否收费
        if (cameraDevice.getIsToll() == 1) {
            log.info("车牌号码【{}】在【{}】从【{}】出场，开始结算费用。", license, nowTimeStr, cameraDevice.getDeviceLocation());
            // 获取月保记录
            MonthlyInsurancePayment insurancePayment = staticMonthlyInsurancePaymentService.getEntity(license);
            // 判断该车辆是否有月保记录
            if (Objects.nonNull(insurancePayment)) {
                // 判断月保是否有效或者是长期
                if (insurancePayment.getLongTerm() == 1 || insurancePayment.getMonthlyStatus().equals("1")) {
                    log.info("该车为有效月保车-开闸放行");
                    // 如果是长期，直接放行
                    GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
                    // 更新出场纪录状态
                    recordCarEnter.setStatus(1);
                } else {
                    // 不是长期月保、或者月保已失效，则按照临停计费，不开闸，在订单支付出监测订单支付成功后开闸
                    log.info("该车不是长期月保、或者月保已失效，则按照临停计费，不开闸，在订单支付出监测订单支付成功后开闸");
                    // 不开闸
                    GateUtil.controlGate(staticControlGate, MyConstants.ZJ_CLOSE, cameraDevice);
                    // 计费时间以月报过期时间为开始时间计算
                    recordCarEnter.setStartTime(insurancePayment.getMonthlyEndTime());
                    // 标记需要生成支付订单
                    flg = true;
                }
            } else {
                // 获取访客车车辆
                CarVisitor visitor = staticCarVisitorService.getByLicensePlates(license);
                // 判断是否有登记为访客车
                if (Objects.nonNull(visitor)) {
                    // 判断是否已经失效，1｜失效；2｜有效
                    if (visitor.getIsFree() == 2) {
                        log.info("该车为有效访客车-开闸放行");
                        // 有效访客车，直接放行
                        GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
                        // 更新出场纪录状态
                        recordCarEnter.setStatus(1);
                    } else {
                        // 无效访客车，则按照临停计费，不开闸，在订单支付出监测订单支付成功后开闸
                        log.info("该车为无效访客车，则按照临停计费，不开闸，在订单支付出监测订单支付成功后开闸");
                        // 不开闸
                        GateUtil.controlGate(staticControlGate, MyConstants.ZJ_CLOSE, cameraDevice);
                        // 计费时间以月报过期时间为开始时间计算
                        recordCarEnter.setStartTime(visitor.getEndTime());
                        // 标记需要生成支付订单
                        flg = true;
                    }
                }
                // 既不是月保车、又不是访客车，就按照临停车计费
                else {
                    // 查询用户是否有领劵
                    ParkCollectCoupons parkCollectCoupons = staticParkCollectCouponsService.getEntityByLicensePlates(license);
                    // 判断用户是否领劵,且订单已支付
                    if (Objects.nonNull(parkCollectCoupons) && parkCollectCoupons.getIsPlay() == 1) {
                        // 查询订单支付时间
                        LocalDateTime playTime = parkCollectCoupons.getPlayTime();
                        // 判断订单支付时间 + 30分钟以后，是否超出了当前系统时间
                        if (playTime.plusMinutes(MyConstants.TIME).isBefore(nowTime)) {
                            // 超出30分钟，不开闸
                            log.info("该车为领劵车，订单已支付，但是超过30分钟，不开闸放行，需要重新计费");
                            // 不开闸
                            GateUtil.controlGate(staticControlGate, MyConstants.ZJ_CLOSE, cameraDevice);
                            // 计费时间以订单支付成功时间 + 30分钟为开始时间计算
                            recordCarEnter.setStartTime(playTime);
                            // 标记需要生成支付订单
                            flg = true;
                        } else {
                            log.info("该车为领劵车，订单已支付，且未超过30分钟，开闸放行");
                            GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
                            // 更新出场纪录状态
                            recordCarEnter.setStatus(1);
                            // 出场时间
                            nowTime = parkCollectCoupons.getEndTime();
                        }
                    } else {
                        // 查询订单是否已经提前支付
                        OrderPaidCatOutbound order = staticOrderPaidCatOutboundService
                                .getEntityByPlates(OrderStatus.SUCCESS.getType(), license);
                        // 判断是否存在订单
                        if (Objects.nonNull(order)) {
                            // 获取支付成功时间
                            LocalDateTime playTime = order.getPlayTime();
                            // 判断订单支付时间 + 30分钟以后，是否超出了当前系统时间
                            if (playTime.plusMinutes(MyConstants.TIME).isBefore(nowTime)) {
                                // 超出30分钟，不开闸
                                log.info("该车为临停车，订单已支付，但是超过30分钟，不开闸放行，需要重新计费");
                                // 不开闸
                                GateUtil.controlGate(staticControlGate, MyConstants.ZJ_CLOSE, cameraDevice);
                                // 计费时间以订单支付成功时间
                                recordCarEnter.setStartTime(playTime);
                                // 标记需要生成支付订单
                                flg = true;
                            } else {
                                log.info("该车为临停车，订单已支付，且未超过30分钟，开闸放行");
                                GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
                                // 更新出场纪录状态
                                recordCarEnter.setStatus(1);
                                // 出场时间
                                nowTime = order.getEndTime();
                            }
                        } else {
                            // 获取免费停车时长：单位：分钟
                            Integer freeDuration = ChargeRulesUtil.getFreeDuration();
                            // 计算两个 LocalDateTime 之间的分钟差
                            Duration duration = Duration.between(recordCarEnter.getStartTime(), nowTime);
                            long minutesDifference = duration.toMinutes();
                            // 判断是否超出免费停车时长
                            if (null != freeDuration && minutesDifference > freeDuration) {
                                // 未领劵，则按照临停计费，不开闸，在订单支付出监测订单支付成功后开闸
                                log.info("该车为临停车，超出免费停车时长，且没有提前支付，不开闸，在订单支付出监测订单支付成功后开闸");
                                // 不开闸
                                GateUtil.controlGate(staticControlGate, MyConstants.ZJ_CLOSE, cameraDevice);
                                // 标记需要生成支付订单
                                flg = true;
                            } else {
                                log.info("该车为临停车，且没有超出免费停车时长，开闸放行");
                                // 开闸
                                GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
                                // 更新出场纪录状态
                                recordCarEnter.setStatus(1);
                            }
                        }
                    }
                }
            }
        } else {
            log.info("车牌号码【{}】在【{}】从【{}】出场，不计费，只做记录，开闸放行。",
                    license, nowTimeStr, cameraDevice.getDeviceLocation());
            GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
            // 更新进场记录状态
            recordCarEnter.setStatus(1);
        }

        // 创建车辆出场记录
        RecordsCarOutbound outboundRecords = createRecordsCarOut(nowTime, recordCarEnter, cameraDevice);
        // 根据标识判断是否需要生成支付订单
        if (flg) temporaryMatters(outboundRecords);
    }

    /**
     * 创建车辆出场记录
     *
     * @param nowTime        出场时间
     * @param recordCarEnter 进场记录
     * @param cameraDevice   设备信息
     * @return
     */
    public static RecordsCarOutbound createRecordsCarOut(LocalDateTime nowTime,
                                                         RecordCarEnter recordCarEnter,
                                                         SystemCameraDevice cameraDevice
    ) {
        // 创建车辆出库对象
        RecordsCarOutbound outboundRecords = new RecordsCarOutbound();
        // 赋值进场时间
        outboundRecords.setStartTime(recordCarEnter.getStartTime());
        outboundRecords.setIsToll(cameraDevice.getIsToll());
        outboundRecords.setMainlandLicensePlates(recordCarEnter.getMainlandLicensePlates());
        outboundRecords.setEndCameraDeviceIp(cameraDevice.getDeviceIp());
        outboundRecords.setEndTime(nowTime);
        outboundRecords.setDeviceGroupId(cameraDevice.getGroupId());
        outboundRecords.setDeviceGroupName(cameraDevice.getDeviceLocation());
        outboundRecords.setCarGroupId(recordCarEnter.getCarGroupId());
        outboundRecords.setCarGroupName(recordCarEnter.getCarGroupName());
        // 通过进场时间和出场时间计算出停车时长是几小时几分
        outboundRecords.setParkingDuration(
                TemporaryUtil.getParkingDuration(
                        outboundRecords.getStartTime(),
                        outboundRecords.getEndTime()
                )
        );
        // 记录车辆出库记录
        staticRecordsCarOutboundService.save(outboundRecords);
        // 更新车辆进场记录
        staticRecordCarEnterService.updateById(recordCarEnter);
        return outboundRecords;
    }

    /**
     * 生成临停订单
     *
     * @param outboundRecords
     */
    private static void temporaryMatters(RecordsCarOutbound outboundRecords) {
        // 默认用户ID为管理员
        String userId = ChargeRulesUtil.getUserId();
        // 查询用户是否有领劵
        ParkCollectCoupons parkCollectCoupons = staticParkCollectCouponsService
                .getEntityByLicensePlates(outboundRecords.getMainlandLicensePlates());
        // 获取商户ID
        if (Objects.nonNull(parkCollectCoupons)) userId = parkCollectCoupons.getUserId();
        // 获取进场时间
        LocalDateTime startTime = outboundRecords.getStartTime();
        // 获取离开场时间
        LocalDateTime endTime = outboundRecords.getEndTime();
        // 生成临停预支付订单
        OrderPaidCatOutbound order = OrderUtils.createOrder(
                // 商户ID
                userId,
                // 实际收入
                TemporaryUtil.temporaryFee(true, startTime, endTime),
                // 总收入
                TemporaryUtil.temporaryFee(false, startTime, endTime),
                // 车辆出场记录
                outboundRecords
        );
        staticOrderPaidCatOutboundService.createOrder(order);
    }

    /**
     * 有车牌用户生成支付订单
     *
     * @param license
     * @return
     */
    public static OrderPaidCatOutbound createOrder(String license, String deviceIp) {
        // 查询用户是否存在未支付订单
        OrderPaidCatOutbound oldEntity = staticOrderPaidCatOutboundService.getEntityBy(license, deviceIp);
        if (Objects.nonNull(oldEntity)) return oldEntity;
        // 查询车辆进场记录
        RecordCarEnter recordCarEnter = staticRecordCarEnterService.getByDeviceIp(license, null);
        // 判断是否存在进场记录
        if (Objects.isNull(recordCarEnter))
            throw new BizException(RespEnum.FAILURE.getCode(), "车牌号为：【" + license + "】暂无进场记录。");
        // 判断车辆是否属于无牌车
        if (StringUtils.isNotBlank(license) && recordCarEnter.getCarGroupId().equals(CarGroupEnum.WU_PAI_CHE.getCarGroupId()))
            throw new BizException(RespEnum.FAILURE.getCode(), "手机号为：【" + license + "】是无牌车，不支持提前支付，请在出口扫码支付。");
        // 判断车辆是否属于临停车
        if (StringUtils.isNotBlank(license) && !recordCarEnter.getCarGroupId().equals(CarGroupEnum.LIN_BAN.getCarGroupId()))
            throw new BizException(RespEnum.FAILURE.getCode(), "车牌号为：【" + license + "】不是临停车，不支持提前支付。");
        // 查询用户是否有领劵
        ParkCollectCoupons parkCollectCoupons = staticParkCollectCouponsService.getEntityByLicensePlates(license);
        // 判断用户是否领劵,且订单已支付
        if (Objects.nonNull(parkCollectCoupons) && parkCollectCoupons.getIsPlay() == 1) {
            // 查询订单支付时间
            LocalDateTime playTime = parkCollectCoupons.getPlayTime();
            // 判断订单支付时间 + 30分钟以后，是否超出了当前系统时间
            if (playTime.plusMinutes(MyConstants.TIME).isBefore(LocalDateTime.now())) {
                // 根据用户领劵信息创建预支付订单
                OrderPaidCatOutbound order = OrderUtils.createOrder(parkCollectCoupons);
                // 重新计算费用
                OrderUtils.countFee(order, playTime);
                return order;
            } else {
                throw new BizException(RespEnum.FAILURE.getCode(), "车牌号为：【" + license + "】已支付，请尽快出场。");
            }
        } else {
            return OrderUtils.createOrder(license, recordCarEnter.getStartTime());
        }
    }

    /**
     * 无车牌用户扫码支付生成预支付订单
     *
     * @param phoneNumber
     * @param deviceIp
     * @return
     */
    public static OrderPaidCatOutbound createOrderByWuChePai(String phoneNumber, String deviceIp) {
        // 查询用户是否存在未支付订单
        OrderPaidCatOutbound oldEntity = staticOrderPaidCatOutboundService.getEntityBy(phoneNumber, deviceIp);
        if (Objects.nonNull(oldEntity)) return oldEntity;
        // 查询车辆进场记录
        RecordCarEnter recordCarEnter = staticRecordCarEnterService.getByDeviceIp(phoneNumber, null);
        // 判断是否存在进场记录
        if (Objects.isNull(recordCarEnter))
            throw new BizException(RespEnum.FAILURE.getCode(), "手机号为：【" + phoneNumber + "】暂无进场记录。");
        // 查询用户是否有领劵
        ParkCollectCoupons parkCollectCoupons = staticParkCollectCouponsService.getEntityByLicensePlates(phoneNumber);
        // 判断用户是否领劵,且订单已支付
        if (Objects.nonNull(parkCollectCoupons) && parkCollectCoupons.getIsPlay() == 1) {
            // 查询订单支付时间
            LocalDateTime playTime = parkCollectCoupons.getPlayTime();
            // 判断订单支付时间 + 30分钟以后，是否超出了当前系统时间
            if (playTime.plusMinutes(MyConstants.TIME).isBefore(LocalDateTime.now())) {
                // 根据用户领劵信息创建预支付订单
                OrderPaidCatOutbound order = OrderUtils.createOrder(parkCollectCoupons);
                // 重新计算费用
                OrderUtils.countFee(order, playTime);
                return order;
            } else {
                throw new BizException(RespEnum.FAILURE.getCode(), "手机号为：【" + phoneNumber + "】已支付，请尽快出场。");
            }
        } else {
            return OrderUtils.createOrder(phoneNumber, recordCarEnter.getStartTime());
        }
    }
}
