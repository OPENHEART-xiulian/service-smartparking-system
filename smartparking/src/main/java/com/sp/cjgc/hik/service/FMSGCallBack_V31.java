package com.sp.cjgc.hik.service;

import com.sp.cjgc.backend.order.CaptureDataUtil;
import com.sp.cjgc.backend.utils.DateTimeUtil;
import com.sp.cjgc.hik.sdk.HCNetSDK;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * @Author: Zoey
 * @Since: 2024-08-15 09:42:12
 * @Description:
 */
@Slf4j
@Component
public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {

    /**
     * 报警信息回调函数数据处理
     *
     * @param lCommand   报警事件类型
     * @param pAlarmer   报警设备信息
     * @param pAlarmInfo 内存区域 该区域存储了与报警相关的详细信息。具体信息的结构和内容取决于 lCommand 的值，即报警事件的类型
     * @param dwBufLen   缓冲区的长度
     * @param pUser      用户数据
     * @return
     */
    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        log.info("报警事件类型： lCommand:{}", Integer.toHexString(lCommand));
        // 车牌抓拍
        if (lCommand == HCNetSDK.COMM_ITS_PLATE_RESULT) {
            // 获取系统当前时间
            LocalDateTime now = DateTimeUtil.getNowTime();

            HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
            strItsPlateResult.write();

            Pointer pItsPlateInfo = strItsPlateResult.getPointer();
            pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
            strItsPlateResult.read();

            try {
                // 车牌号
                String sLicense = new String(strItsPlateResult.struPlateInfo.sLicense, "GBK");
                // 设备ip
                String cameraIp = new String(pAlarmer.sDeviceIP).trim();
                // 设备端口
                short wLinkPort = pAlarmer.wLinkPort;
                // 记录车辆进出记录
                CaptureDataUtil.createCaptureData(sLicense, cameraIp, wLinkPort);
                log.info("设备IP：【{}】端口号：【{}】在【{}】抓拍到的车牌号：{}", cameraIp, wLinkPort, DateTimeUtil.getNowTimeStr(now), sLicense);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                log.error("抓拍车牌号转换失败", e1);
            }
        }
        return true;
    }
}
