package com.sp.cjgc.backend.endpoint.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 09:37:02
 * @Description:
 */
public class HomePageReq implements Serializable {
    private static final long serialVersionUID = -24599605854870981L;

    @Getter
    @Setter
    @ApiModel(value = "HomePageReq.QueryDTO", description = "首页-订单统计输入DTO")
    public static class QueryDTO implements Serializable {
        private static final long serialVersionUID = 303840512429333818L;
        //@formatter:off
        @ApiModelProperty(value = "年份", position = 1, required = true)
        private String year;
        @ApiModelProperty(value = "商户ID", position = 2, required = true)
        private String userId;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "HomePageReq.Query1DTO", description = "首页-收益统计输入DTO")
    public static class Query1DTO implements Serializable {
        private static final long serialVersionUID = 303840512429333818L;
        //@formatter:off
        @ApiModelProperty(value = "商户ID", position = 1, required = true)
        private String userId;
        //@formatter:on
    }
}
