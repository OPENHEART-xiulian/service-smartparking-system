package com.sp.cjgc.backend.order;

import com.sp.cjgc.MyConstants;
import com.sp.cjgc.backend.service.SystemChargeRulesService;
import com.sp.cjgc.backend.service.SystemUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: Zoey
 * @Since: 2024-08-15 14:57:41
 * @Description: 获取收费规则工具类
 */
@Component
public class ChargeRulesUtil {

    @Autowired
    private SystemUsersService systemUsersService;
    @Autowired
    private SystemChargeRulesService systemChargeRulesService;

    private static SystemUsersService statusSystemUsersService;
    private static SystemChargeRulesService statusChargeRulesService;

    @PostConstruct
    public void init() {
        statusSystemUsersService = this.systemUsersService;
        statusChargeRulesService = this.systemChargeRulesService;
    }

    /**
     * 获取免费停车时长：单位：分钟
     *
     * @return
     */
    public static Integer getFreeDuration() {
        // 默认免费停车 15 分钟
        Integer freeDuration = MyConstants.FREE_DURATION;
        if (statusChargeRulesService != null
                && statusChargeRulesService.getEntity() != null
                && statusChargeRulesService.getEntity().getFreeDuration() != null
        ) {
            freeDuration = statusChargeRulesService.getEntity().getFreeDuration();
        }
        return freeDuration;
    }

    /**
     * 获取临保收费标准，单位：元/小时
     *
     * @return
     */
    public static String getTollStandard() {
        // 默认 15元/小时
        String tollStandard = MyConstants.TOOL_STANDARD;
        if (statusChargeRulesService != null
                && statusChargeRulesService.getEntity() != null
                && statusChargeRulesService.getEntity().getTollStandard() != null
        ) {
            tollStandard = statusChargeRulesService.getEntity().getTollStandard();
        }
        return tollStandard;
    }

    /**
     * 获取临保收费上限。单位：元/24小时
     *
     * @return
     */
    public static String getFeeCap() {
        // 默认 99元/24小时
        String tollStandard = MyConstants.CAPPING_FEE;
        if (statusChargeRulesService != null
                && statusChargeRulesService.getEntity() != null
                && statusChargeRulesService.getEntity().getFeeCap() != null
        ) {
            tollStandard = statusChargeRulesService.getEntity().getFeeCap();
        }
        return tollStandard;
    }

    /**
     * 获取月保收费标准-内部车辆，单位：元/月
     *
     * @return
     */
    public static String getMonthlyInternalCar() {
        if (statusChargeRulesService != null) {
            return statusChargeRulesService.getEntity().getMonthlyInternalCar();
        }
        return null;
    }

    /**
     * 获取月保收费标准-所属企业公车，单位：元/月
     *
     * @return
     */
    public static String getMonthlyEnterpriseCar() {
        if (statusChargeRulesService != null) {
            return statusChargeRulesService.getEntity().getMonthlyEnterpriseCar();
        }
        return null;
    }

    /**
     * 获取月保收费标准-外部车辆(非机械车位)，单位：元/月
     *
     * @return
     */
    public static String getMonthlyInternalCarNoMachinery() {
        if (statusChargeRulesService != null) {
            return statusChargeRulesService.getEntity().getMonthlyInternalCarNoMachinery();
        }
        return null;
    }

    /**
     * 获取月保收费标准-外部车辆(机械车位)，单位：元/月
     *
     * @return
     */
    public static String getMonthlyExternalCarMachinery() {
        if (statusChargeRulesService != null) {
            return statusChargeRulesService.getEntity().getMonthlyExternalCarMachinery();
        }
        return null;
    }

    /**
     * 查询管理员用户ID
     *
     * @return
     */
    public static String getUserId() {
        return statusSystemUsersService.getEntityId();
    }
}
