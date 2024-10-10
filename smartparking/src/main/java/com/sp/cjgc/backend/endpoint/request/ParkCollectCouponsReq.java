package com.sp.cjgc.backend.endpoint.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商户-停车领劵(ParkCollectCoupons)输入DTO
 *
 * @author zoey
 * @since 2024-08-22 09:56:37
 */
public class ParkCollectCouponsReq implements Serializable {
    private static final long serialVersionUID = 701842395678353080L;

    @Getter
    @Setter
    @ApiModel(value = "ParkCollectCouponsReq.CreateDTO", description = "商户-停车领劵新增DTO")
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

    @Getter
    @Setter
    @ApiModel(value = "ParkCollectCouponsReq.CountDTO", description = "商户-统计剩余停车劵DTO")
    public static class CountDTO implements Serializable {
        private static final long serialVersionUID = -66837852169218985L;
        //@formatter:off
        @ApiModelProperty(value = "商户ID", position = 1, required = true)
        private String userId;
        //@formatter:on
    }
}
