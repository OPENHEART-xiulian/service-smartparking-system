package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商户-对账记录(MerchantReconciliation)输入DTO
 *
 * @author zoey
 * @since 2024-08-20 13:13:44
 */
public class MerchantReconciliationReq implements Serializable {
    private static final long serialVersionUID = 859997364745937647L;

    @Getter
    @Setter
    @ApiModel(value = "MerchantReconciliationReq.QueryDTO", description = "商户-对账记录查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = -46803096107505314L;
        //@formatter:off
        @ApiModelProperty(value = "商家名称", position = 1)
        private String userName;
        @ApiModelProperty(value = "年份和月份", position = 2)
        private String yearNumber;
        @ApiModelProperty(value = "发放状态", position = 4)
        private String status;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "MerchantReconciliationReq.UpdateStatusDTO", description = "商户-确认发放输入DTO")
    public static class UpdateStatusDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = -46803096107505314L;
        //@formatter:off
        @ApiModelProperty(value = "对账记录ID", position = 1, required = true)
        private String id;
        //@formatter:on
    }
}
