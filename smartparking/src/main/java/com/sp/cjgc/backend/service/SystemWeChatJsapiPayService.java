package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.SystemWeChatJsapiPay;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统管理-微信JSAPI支付配置(SystemWeChatJsapiPay)表服务接口
 *
 * @author zoey
 * @since 2024-09-06 14:05:27
 */
public interface SystemWeChatJsapiPayService extends IService<SystemWeChatJsapiPay> {

    /**
     * 微信JSAPI支付配置-查询
     *
     * @return
     */
    SystemWeChatJsapiPay getWeChatJsapiPay();

    /**
     * 公众号APPID-查询
     *
     * @return
     */
    String getAppId();

    /**
     * 微信JSAPI支付配置-新增或更新
     *
     * @param entity
     * @return
     */
    SystemWeChatJsapiPay createOrUpdate(SystemWeChatJsapiPay entity);

    /**
     * 上传私钥文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    String uploadPrivateKey(MultipartFile file);
}
