package com.sp.cjgc.common.swagger;

import com.sp.cjgc.common.properties.utils.SwaggerPropertiesUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Zoey
 * @Since: 2024-06-07 13:08:12
 * @Description:
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class Knife4jConfig {

    @Bean
    public Docket createTestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建API的基本信息，这些信息会在Swagger UI中进行显示
     *
     * @return API的基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title(SwaggerPropertiesUtil.title())
                // API描述
                .description(SwaggerPropertiesUtil.description())
                //接口的版本
                .version(SwaggerPropertiesUtil.version())
                .build();
    }
}