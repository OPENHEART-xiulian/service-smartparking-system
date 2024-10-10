package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统管理-车位数量明细表(SystemParkingDetail)输入DTO
 *
 * @author zoey
 * @since 2024-08-15 16:24:56
 */
public class SystemParkingDetailReq implements Serializable {
    private static final long serialVersionUID = 693613011290114766L;

    @Getter
    @Setter
    @ApiModel(value = "SystemParkingDetailReq.CreateOrUpdateDTO", description = "系统管理-车位数量明细表新增或更新DTO")
    public static class CreateOrUpdateDTO implements Serializable {
        private static final long serialVersionUID = 709474663152614343L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1, required = true)
        private String id;               
        @ApiModelProperty(value = "用户ID", position = 2, required = true)
        private String userId;           
        @ApiModelProperty(value = "租赁车位数量", position = 3, required = true)
        private Integer assignedNumber;  
        @ApiModelProperty(value = "开始时间,时间格式:yyyy-MM-dd HH:mm:ss", position = 4)
        private String startTime;
        @ApiModelProperty(value = "结束时间,时间格式:yyyy-MM-dd HH:mm:ss", position = 5)
        private String endTime;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemParkingDetailReq.QueryDTO", description = "系统管理-车位数量明细表查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = 860890907888287960L;
        //@formatter:off
        @ApiModelProperty(value = "开始时间,时间格式:yyyy-MM-dd HH:mm:ss", position = 1)
        private String startTime;
        @ApiModelProperty(value = "结束时间,时间格式:yyyy-MM-dd HH:mm:ss", position = 2)
        private String endTime;
        @ApiModelProperty(value = "租赁车位数量", position = 3)
        private Integer assignedNumber;
        @ApiModelProperty(value = "用户名称", position = 4)
        private String userName;
        @ApiModelProperty(value = "租赁状态;1｜在租; 2｜过租", position = 5)
        private Integer assignedStatus;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemParkingDetailReq.DeleteDTO", description = "系统管理-车位数量明细表删除DTO")
    public static class DeleteDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = 860890907888287960L;
        //@formatter:off
        @ApiModelProperty(value = "id集合", position = 1)
        private List<String> ids;
        //@formatter:on
    }
}
