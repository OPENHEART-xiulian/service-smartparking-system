package com.sp.cjgc.backend.endpoint.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 16:14:41
 * @Description:
 */
public class ApiReq implements Serializable {
    private static final long serialVersionUID = 701842395678353080L;

    @Getter
    @Setter
    @ApiModel(value = "ApiReq.QueryDTO", description = "商户-停车领劵新增DTO")
    public static class QueryDTO implements Serializable {
        private static final long serialVersionUID = -66837852169218985L;
        //@formatter:off
        @ApiModelProperty(value = "商户ID", position = 1, required = true)
        private String userId;
        @ApiModelProperty(value = "时间戳，用于验证二维码的有效时间", position = 2)
        private Long timestamp;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "ApiReq.CreateDTO", description = "商户-停车领劵新增DTO")
    public static class CreateDTO implements Serializable {
        private static final long serialVersionUID = -66837852169218985L;
        //@formatter:off
        @ApiModelProperty(value = "商户ID", position = 1, required = true)
        private String userId;
        @ApiModelProperty(value = "车牌号/手机号码(无牌车)", position = 2, required = true)
        private String plates;
        @ApiModelProperty(value = "时间戳，用于验证二维码的有效时间", position = 3)
        private Long timestamp;
        //@formatter:on
    }
}
