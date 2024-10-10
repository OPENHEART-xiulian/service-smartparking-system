package com.sp.cjgc.backend.order;

import com.sp.cjgc.MyConstants;
import com.sp.cjgc.backend.domain.CarVisitor;
import com.sp.cjgc.backend.domain.MonthlyInsurancePayment;
import com.sp.cjgc.backend.domain.RecordCarEnter;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.enums.CarGroupEnum;
import com.sp.cjgc.backend.service.CarVisitorService;
import com.sp.cjgc.backend.service.MonthlyInsurancePaymentService;
import com.sp.cjgc.backend.service.RecordCarEnterService;
import com.sp.cjgc.backend.utils.DateTimeUtil;
import com.sp.cjgc.hik.utils.GateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Zoey
 * @Since: 2024-09-02 10:47:40
 * @Description: 车辆进场记录工具类
 */
@Slf4j
@Component
public class EnterParkingUtil {

    // 是否开启控制闸 true|开启  false｜关闭
    @Value("${global.controlGate}")
    private boolean controlGate;
    @Autowired
    private CarVisitorService carVisitorService;
    @Autowired
    private RecordCarEnterService recordCarEnterService;
    @Autowired
    private MonthlyInsurancePaymentService monthlyInsurancePaymentService;

    private static boolean staticControlGate;
    private static CarVisitorService staticCarVisitorService;
    private static RecordCarEnterService staticRecordCarEnterService;
    private static MonthlyInsurancePaymentService staticMonthlyInsurancePaymentService;

    @PostConstruct
    public void init() {
        staticControlGate = this.controlGate;
        staticCarVisitorService = this.carVisitorService;
        staticRecordCarEnterService = this.recordCarEnterService;
        staticMonthlyInsurancePaymentService = this.monthlyInsurancePaymentService;
    }

    /**
     * 根据摄像头抓拍信息创建车辆进场记录(有车牌的抓拍信息)
     *
     * @param license      车牌号
     * @param cameraDevice 摄像头设备信息
     */
    public static void createRecordCarEnter(String license, SystemCameraDevice cameraDevice) {
        // 判断是否有车牌号，且是否是开始计费进口
        if (CarGroupEnum.WU_PAI_CHE.getCarGroupName().equals(license)
                && cameraDevice.getIsToll() == 1
        ) {
            log.info("无车牌号，不自动创建进场记录,请扫码进场");
            // 不开闸
            GateUtil.controlGate(staticControlGate, MyConstants.ZJ_CLOSE, cameraDevice);
            return;
        }
        // 获取系统当前时间
        LocalDateTime nowTime = DateTimeUtil.getNowTime();
        // 将当前时间格式化 yyyy-MM-dd HH:mm:ss
        String nowTimeStr = DateTimeUtil.getNowTimeStr(nowTime);
        log.info("车牌号码【{}】在【{}】从【{}】进场", license, nowTimeStr, cameraDevice.getDeviceLocation());

        /********************************开始创建车辆进场记录**************************************/
        // 获取车辆分组信息
        Map<String, Object> params = setCarGroup(license);
        Integer carGroupId = (Integer) params.get("carGroupId");
        String carGroupName = params.get("carGroupName").toString();
        // 查询未结束订单中进场时间为最早的记录，根据设备分组ID和车牌号查询，防止重复记录
        RecordCarEnter recordCarEnter = staticRecordCarEnterService.getByPlates(cameraDevice.getGroupId(), license);
        // 判断该摄像头是否已经记录了该车牌号的进场记录
        if (Objects.isNull(recordCarEnter)) recordCarEnter = new RecordCarEnter();
        recordCarEnter.setIsToll(cameraDevice.getIsToll());
        recordCarEnter.setMainlandLicensePlates(license);
        recordCarEnter.setStartCameraDeviceIp(cameraDevice.getDeviceIp());
        recordCarEnter.setDeviceGroupId(cameraDevice.getGroupId());
        recordCarEnter.setDeviceGroupName(cameraDevice.getDeviceLocation());
        recordCarEnter.setCarGroupId(carGroupId);
        recordCarEnter.setCarGroupName(carGroupName);
        // 默认未结束
        recordCarEnter.setStatus(0);

        // 判断是否 是开始计费的进口
        if (recordCarEnter.getIsToll() == 1 && recordCarEnter.getStartTime() != null) {
            // 如果计费，则是按照最早的进入时间为准
            recordCarEnter.setStartTime(recordCarEnter.getStartTime());
        } else {
            // 赋值入场时间
            recordCarEnter.setStartTime(nowTime);
        }
        // 新增或更新车辆入库记录
        staticRecordCarEnterService.saveOrUpdate(recordCarEnter);
        /********************************结束创建车辆进场记录**************************************/
        // 开闸放行
        GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
    }

    /**
     * 无车牌扫码进场时，创建车辆进场记录
     *
     * @param phoneNumber  手机号码
     * @param cameraDevice 摄像头设备信息
     */
    public static void createRecordCarEnterByPhoneNumber(String phoneNumber, SystemCameraDevice cameraDevice) {
        // 获取系统当前时间
        LocalDateTime nowTime = DateTimeUtil.getNowTime();
        // 将当前时间格式化 yyyy-MM-dd HH:mm:ss
        String nowTimeStr = DateTimeUtil.getNowTimeStr(nowTime);
        log.info("手机号码【{}】的车辆在【{}】从【{}】进场", phoneNumber, nowTimeStr, cameraDevice.getDeviceLocation());

        /********************************开始创建车辆进场记录**************************************/
        // 查询未结束订单中进场时间为最早的记录，根据设备分组ID和车牌号查询，防止重复记录
        RecordCarEnter recordCarEnter = staticRecordCarEnterService.getByPlates(cameraDevice.getGroupId(), phoneNumber);
        // 判断该摄像头是否已经记录了该车牌号的进场记录
        if (Objects.isNull(recordCarEnter)) recordCarEnter = new RecordCarEnter();
        recordCarEnter.setIsToll(cameraDevice.getIsToll());
        recordCarEnter.setMainlandLicensePlates(phoneNumber);
        recordCarEnter.setStartCameraDeviceIp(cameraDevice.getDeviceIp());
        recordCarEnter.setDeviceGroupId(cameraDevice.getGroupId());
        recordCarEnter.setDeviceGroupName(cameraDevice.getDeviceLocation());
        recordCarEnter.setCarGroupId(CarGroupEnum.WU_PAI_CHE.getCarGroupId());
        recordCarEnter.setCarGroupName(CarGroupEnum.WU_PAI_CHE.getCarGroupName());
        // 默认未结束
        recordCarEnter.setStatus(0);

        // 判断是否 是开始计费的进口
        if (recordCarEnter.getIsToll() == 1 && recordCarEnter.getStartTime() != null) {
            // 如果计费，则是按照最早的进入时间为准
            recordCarEnter.setStartTime(recordCarEnter.getStartTime());
        } else {
            // 赋值入场时间
            recordCarEnter.setStartTime(nowTime);
        }
        // 新增或更新车辆入库记录
        staticRecordCarEnterService.saveOrUpdate(recordCarEnter);
        /********************************结束创建车辆进场记录**************************************/
        // 开闸放行
        GateUtil.controlGate(staticControlGate, MyConstants.ZJ_OPEN, cameraDevice);
    }

    /**
     * 设置车辆分组信息
     *
     * @param licensePlate
     * @return
     */
    private static Map<String, Object> setCarGroup(String licensePlate) {
        // 创建返回值对象
        Map<String, Object> params = new HashMap<>();
        //车辆分组编码，1｜临保；2｜月保；3｜访客车；4｜无牌车；5|其他；默认临保
        Integer carGroupId = CarGroupEnum.LIN_BAN.getCarGroupId();
        //车辆分组名称 默认临保
        String carGroupName = CarGroupEnum.LIN_BAN.getCarGroupName();
        // 判断是否是无牌车
        if (licensePlate.equals(CarGroupEnum.WU_PAI_CHE.getCarGroupName())) {
            // 无牌车
            carGroupId = CarGroupEnum.WU_PAI_CHE.getCarGroupId();
        } else {
            // 查询车辆是否为访客车
            CarVisitor visitor = staticCarVisitorService.getByLicensePlates(licensePlate);
            // 访客车
            if (Objects.nonNull(visitor)) {
                carGroupId = CarGroupEnum.FANG_KA.getCarGroupId();
                carGroupName = CarGroupEnum.FANG_KA.getCarGroupName();
            } else {
                // 获取月保记录
                MonthlyInsurancePayment insurancePayment = staticMonthlyInsurancePaymentService.getEntity(licensePlate);
                if (Objects.nonNull(insurancePayment)) {
                    // 判断是否是长期
                    if (insurancePayment.getLongTerm() == 1 || insurancePayment.getMonthlyStatus().equals("1")) {
                        carGroupId = CarGroupEnum.YUE_BAN.getCarGroupId();
                        carGroupName = CarGroupEnum.YUE_BAN.getCarGroupName();
                    }
                }
            }
        }
        params.put("carGroupId", carGroupId);
        params.put("carGroupName", carGroupName);
        return params;
    }
}
