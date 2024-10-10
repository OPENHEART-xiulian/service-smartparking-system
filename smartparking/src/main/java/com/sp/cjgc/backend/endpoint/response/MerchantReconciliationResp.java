package com.sp.cjgc.backend.endpoint.response;

import com.sp.cjgc.common.pageutil.PageInfoResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 商户-对账记录(MerchantReconciliation)输出DTO
 *
 * @author zoey
 * @since 2024-08-20 13:13:44
 */
public class MerchantReconciliationResp implements Serializable {
    private static final long serialVersionUID = 535739998420120134L;
    
    @Getter
    @Setter
    @ApiModel(value = "MerchantReconciliationResp.MerchantReconciliationDTO", description = "商户-对账记录输出DTO")
    public static class MerchantReconciliationDTO implements Serializable {
        private static final long serialVersionUID = 129150344949360494L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                  
        @ApiModelProperty(value = "用户ID", position = 2)
        private String userId;              
        @ApiModelProperty(value = "总停车时长", position = 3)
        private String totalDuration;       
        @ApiModelProperty(value = "总计金额", position = 4)
        private String totalAmount;         
        @ApiModelProperty(value = "总计优惠金额", position = 5)
        private String totalDiscountAmount; 
        @ApiModelProperty(value = "总计收入金额", position = 6)
        private String totalIncomeAmount;   
        @ApiModelProperty(value = "年份和月份", position = 7)
        private String yearNumber;          
        @ApiModelProperty(value = "发放状态", position = 9)
        private String status;
        @ApiModelProperty(value = "用户名称", position = 10)
        private String userName;
        @ApiModelProperty(value = "租赁车位数量", position = 11)
        private Integer assignedNumber;
        //@formatter:on
    }
    
    @Getter
    @Setter
    @ApiModel(value = "MerchantReconciliationResp.ListDTO", description = "商户-对账记录-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<MerchantReconciliationDTO> list;
    }
}
