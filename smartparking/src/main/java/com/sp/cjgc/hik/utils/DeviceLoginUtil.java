package com.sp.cjgc.hik.utils;

import com.sp.cjgc.hik.sdk.HCNetSDK;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zoey
 * @Since: 2024-08-15 09:37:35
 * @Description:
 */
@Slf4j
public class DeviceLoginUtil {

    //    /**
//     * linux系统动态库路径(这是海康SDK在服务器上的存放地址)
//     */
//    private static final String url = "/smartParking/backend/linux-hksdk";
//
//    /**
//     * 动态库加载
//     *
//     * @return
//     */
//    public static HCNetSDK createSDKInstance() {
//        synchronized (HCNetSDK.class) {
//            String strDllPath = "";
//            try {
//                //Linux系统加载库路径
//                strDllPath = url + "/libhcnetsdk.so";
//                return (HCNetSDK) Native.loadLibrary(strDllPath, HCNetSDK.class);
//            } catch (Exception ex) {
//                log.error("加载库地址: 【{}】 异常原因: 【{}】", strDllPath, ex.getMessage());
//                throw new BizException(RespEnum.FAILURE.getCode(), "加载库失败");
//            }
//        }
//    }

    /**
     * 官方的说法：linux系统建议调用以下接口加载组件库
     *
     * @param hcNetSDK 海康SDK接口
     */
    public static void isLinux(HCNetSDK hcNetSDK) {
        HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
        HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
        //这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
        String strPath1 = HCNetSDK.prodUrl + "/libcrypto.so.1.1";
        String strPath2 = HCNetSDK.prodUrl + "/libssl.so.1.1";

        System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
        ptrByteArray1.write();
        hcNetSDK.NET_DVR_SetSDKInitCfg(3, ptrByteArray1.getPointer());

        System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
        ptrByteArray2.write();
        hcNetSDK.NET_DVR_SetSDKInitCfg(4, ptrByteArray2.getPointer());

        String strPathCom = HCNetSDK.prodUrl;

        HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
        System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
        struComPath.write();
        hcNetSDK.NET_DVR_SetSDKInitCfg(2, struComPath.getPointer());
    }

    /**
     * 设备登录V40 与V30功能一致
     *
     * @param hcNetSDK       海康SDK接口
     * @param deviceIp       设备IP
     * @param devicePort     设备端口号
     * @param deviceUserName 设备账号
     * @param devicePassword 设备密码
     * @return lUserID 用户句柄
     */
    public static int login_V40(HCNetSDK hcNetSDK,
                                String deviceIp, String devicePort, String deviceUserName, String devicePassword
    ) {
        //设置连接时间与重连时间
        hcNetSDK.NET_DVR_SetConnectTime(2000, 1);
        hcNetSDK.NET_DVR_SetReconnect(50000, true);

        //设备信息, 输出参数
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();

        // 注册设备-登录参数，包括设备地址、登录用户、密码等
        m_strLoginInfo.sDeviceAddress = new byte[hcNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(deviceIp.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, deviceIp.length());

        m_strLoginInfo.sUserName = new byte[hcNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(deviceUserName.getBytes(), 0, m_strLoginInfo.sUserName, 0, deviceUserName.length());

        m_strLoginInfo.sPassword = new byte[hcNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(devicePassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, devicePassword.length());
        // 设备端口号
        m_strLoginInfo.wPort = Short.parseShort(devicePort);
        //是否异步登录：0- 否，1- 是， 为0时登录为同步模式，接口返回成功即表示登录成功 、为1时登录为异步模式，登录是否成功在输入参数设置的回调函数中返回
        m_strLoginInfo.bUseAsynLogin = false;
        m_strLoginInfo.write();

        //设备信息, 输出参数
        int lUserID = hcNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (lUserID == -1) {
            log.info("设备IP为：{}, 信息输出参数： {}", deviceIp, hcNetSDK.NET_DVR_GetErrorMsg(null));
            hcNetSDK.NET_DVR_Cleanup();
            // 抛出异常
            return lUserID;
        }
        return lUserID;
    }

    /**
     * 登录设备
     *
     * @param hcNetSDK       海康SDK接口
     * @param lUserID        用户句柄
     * @param deviceIp       设备IP
     * @param devicePort     设备端口
     * @param deviceUserName 设备登陆账号
     * @param devicePassword 设备登陆密码
     * @return lUserID 用户句柄
     */
    public static int loginDevice(HCNetSDK hcNetSDK, int lUserID,
                                  String deviceIp, String devicePort, String deviceUserName, String devicePassword
    ) {
        // 判断是否需要登陆设备
        if (lUserID == -1) {
            // 登录设备
            lUserID = DeviceLoginUtil.login_V40(hcNetSDK, deviceIp, devicePort, deviceUserName, devicePassword);
            log.info("登录设备后返回的用户句柄：【{}】", lUserID);
        }
        // 如果登陆失败，重连三次
        if (lUserID == -1) {
            int i = 1;
            while (true) {
                try {
                    log.info("设备IP：【{}】 第【{}】次连接失败，重新连接...", deviceIp, i);
                    // 重连
                    lUserID = DeviceLoginUtil.login_V40(hcNetSDK, deviceIp, devicePort, deviceUserName, devicePassword);
                    // 连接成功，跳出循环
                    if (lUserID > -1) {
                        log.info("设备IP：【{}】 第【{}】次连接成功", deviceIp, i);
                        break;
                    }
                    if (i > 3) {
                        log.info("设备IP：【{}】 第【{}】次连接失败，请检查设备是否正常", deviceIp, i);
                        break;
                    }
                    i++;
                } catch (Exception e) {
                    log.error("心跳监测线程中断", e);
                }
            }
        }
        return lUserID;
    }

    /**
     * 设备撤防、设备注销、释放SDK资源
     *
     * @param hcNetSDK     海康SDK接口
     * @param lUserID      用户句柄
     * @param lAlarmHandle 布防句柄
     * @param deviceIp     设备IP
     */
    public static void cleanup(HCNetSDK hcNetSDK, int lUserID, int lAlarmHandle, String deviceIp) {
        log.info("设备IP：【{}】 撤防的时候用户句柄: {}", deviceIp, lUserID);
        log.info("设备IP：【{}】 撤防的时候报警布防句柄: {}", deviceIp, lAlarmHandle);
        if (lAlarmHandle > -1) {
            if (hcNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle)) log.info("设备IP：【{}】撤防成功", deviceIp);
        }
        if (lUserID > -1) {
            if (hcNetSDK.NET_DVR_Logout(lUserID)) log.info("设备IP：【{}】注销成功", deviceIp);
        }
        //释放SDK
        hcNetSDK.NET_DVR_Cleanup();
        log.info("设备IP：【{}】 释放SDK资源成功", deviceIp);
    }

    /**
     * 报警布防
     *
     * @param hcNetSDK     海康SDK接口
     * @param lUserID      用户句柄
     * @param lAlarmHandle 布防句柄
     * @param keepRunning  是否断开与设备之间的长连接  false｜需要断开与设备之间的长连接 true｜不需要断开与设备之间的长连接
     * @param deviceIp     设备IP
     * @return keepRunning 是否断开与设备之间的长连接
     */
    public static boolean setAlarm(HCNetSDK hcNetSDK, int lUserID, int lAlarmHandle, boolean keepRunning, String deviceIp) {
        //尚未布防,需要布防
        if (lAlarmHandle < 0) {
            //报警布防参数设置
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            m_strAlarmInfo.dwSize = m_strAlarmInfo.size();
            // 布防等级
            m_strAlarmInfo.byLevel = 1;
            // 智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
            m_strAlarmInfo.byAlarmInfoType = 1;
            // 布防类型：0-客户端布防，1-实时布防
            m_strAlarmInfo.byDeployType = 0;
            // 抓拍，这个类型要设置为 0 ，最重要的一点设置
            m_strAlarmInfo.byFaceAlarmDetection = 0;
            m_strAlarmInfo.write();
            lAlarmHandle = hcNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, m_strAlarmInfo);
            if (lAlarmHandle == -1) {
                log.info("设备IP：【{}】布防失败，错误码为：【{}】", deviceIp, hcNetSDK.NET_DVR_GetLastError());
                keepRunning = false;
            } else {
                log.info("设备IP：【{}】布防成功", deviceIp);
            }
        } else {
            log.info("设备IP：【{}】已经布防！布防句柄【{}】", deviceIp, lAlarmHandle);
        }
        return keepRunning;
    }
}
