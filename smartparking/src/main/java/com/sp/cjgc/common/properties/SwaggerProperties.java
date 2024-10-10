package com.sp.cjgc.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Zoey
 * @Since: 2024-06-07 13:09:16
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /**
     * 是否开启swagger文档
     */
    private Boolean enable;

    /**
     * swagger接口文档标题
     */
    private String title;

    /**
     * swagger接口文档说明
     */
    private String description;

    /**
     * swagger接口文档版本号
     */
    private String version;
}
