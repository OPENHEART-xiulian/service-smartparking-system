package com.sp.cjgc.common.properties.utils;

import com.sp.cjgc.common.properties.SwaggerProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: Zoey
 * @Since: 2024-06-07 13:09:52
 * @Description:
 */
@Component
public class SwaggerPropertiesUtil {

    private static SwaggerProperties swaggerPropertiesConfig;

    private final SwaggerProperties swaggerProperties;

    public SwaggerPropertiesUtil(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @PostConstruct
    private void init() {
        swaggerPropertiesConfig = swaggerProperties;
    }

    /**
     * 是否开启swagger文档
     *
     * @return
     */
    public static Boolean enable() {
        return swaggerPropertiesConfig.getEnable();
    }

    /**
     * swagger接口文档标题
     *
     * @return
     */
    public static String title() {
        return swaggerPropertiesConfig.getTitle();
    }

    /**
     * swagger接口文档说明
     *
     * @return
     */
    public static String description() {
        return swaggerPropertiesConfig.getDescription();
    }

    /**
     * swagger接口文档版本号
     *
     * @return
     */
    public static String version() {
        return swaggerPropertiesConfig.getVersion();
    }
}
