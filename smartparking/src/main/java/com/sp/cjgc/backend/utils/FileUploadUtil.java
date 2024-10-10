package com.sp.cjgc.backend.utils;

import cn.hutool.core.util.IdUtil;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.common.exception.BizException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zoey
 * @Since: 2024-08-14 17:21:33
 * @Description: 文件上传工具类
 */
@Component
public class FileUploadUtil {

    @Value("${file.root}")
    private String fileRoot;

    @Value("${file.thirdParty}")
    private Boolean thirdParty;

    /**
     * 文件上传,根据文件名判断，文件存在就覆盖，文件不存在就上传
     *
     * @param files 需要上传的文件
     * @return
     */
    public List<String> upload(MultipartFile[] files) {
        List<String> fileDTOS = new ArrayList<>();
        // 图片审核
        try {
            for (int i = 0; i < files.length; i++) {
                // 文件名称
                String fileName = replaceFileName(files[i]);
                // 拼接文件上传地址
                String fileUrl = fileRoot + fileName;
                // 判断是否开启了用第三方服务器作为文件存储
                if (thirdParty) {
                    // 上传到第三方服务器
                    UploadUtils.uploadToServerB(files[i].getBytes(), fileRoot, fileName);
                } else {
                    File dest = new File(fileUrl);
                    FileUtils.forceMkdirParent(dest);
                    files[i].transferTo(dest);
                }
                fileDTOS.add(fileUrl);
            }
            return fileDTOS;
        } catch (Exception e) {
            throw new BizException(RespEnum.FAILURE);
        }
    }

    public static String replaceFileName(MultipartFile file) {
        // 获取原本的文件名称
        String originalFilename = file.getOriginalFilename();
        // 截取原本文件名称的后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        return IdUtil.fastUUID() + suffixName;
    }
}
