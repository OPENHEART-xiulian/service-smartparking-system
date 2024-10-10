package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.SystemWeChatJsapiPay;
import com.sp.cjgc.backend.mapper.SystemWeChatJsapiPayMapper;
import com.sp.cjgc.backend.service.SystemWeChatJsapiPayService;
import com.sp.cjgc.backend.utils.UploadUtils;
import com.sp.cjgc.common.exception.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

/**
 * 系统管理-微信JSAPI支付配置(SystemWeChatJsapiPay)表服务实现类
 *
 * @author zoey
 * @since 2024-09-06 14:05:29
 */
@Service
public class SystemWeChatJsapiPayServiceImpl extends ServiceImpl<SystemWeChatJsapiPayMapper, SystemWeChatJsapiPay> implements SystemWeChatJsapiPayService {

    @Value("${file.privateKeyPath}")
    private String privateKeyPath;

    @Value("${file.thirdParty}")
    private Boolean thirdParty;

    /**
     * 微信JSAPI支付配置-查询
     *
     * @return
     */
    @Override
    public SystemWeChatJsapiPay getWeChatJsapiPay() {
        return this.getOne(Wrappers.<SystemWeChatJsapiPay>lambdaQuery());
    }

    /**
     * 公众号APPID-查询
     *
     * @return
     */
    @Override
    public String getAppId() {
        return this.getOne(Wrappers.<SystemWeChatJsapiPay>lambdaQuery()).getAppId();
    }

    /**
     * 微信JSAPI支付配置-新增或更新
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemWeChatJsapiPay createOrUpdate(SystemWeChatJsapiPay entity) {
        // 查询对象是否存在
        SystemWeChatJsapiPay oldEntity = this.getWeChatJsapiPay();
        // 判断是否已经存在数据
        if (Objects.nonNull(oldEntity)) entity.setId(oldEntity.getId());
        this.saveOrUpdate(entity);
        return entity;
    }

    /**
     * 上传私钥文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public String uploadPrivateKey(MultipartFile file) {
        try {
            // 获取原本的文件名称
            String fileName = file.getOriginalFilename();
            // 拼接文件上传地址
            String fileUrl = privateKeyPath + fileName;
            // 判断是否开启了用第三方服务器作为文件存储
            if (thirdParty) {
                // 上传到第三方服务器
                UploadUtils.uploadToServerB(file.getBytes(), privateKeyPath, fileName);
            } else {
                File dest = new File(fileUrl);
                file.transferTo(dest);
            }
            return fileUrl;
        } catch (Exception e) {
            throw new BizException(RespEnum.FAILURE);
        }
    }
}
