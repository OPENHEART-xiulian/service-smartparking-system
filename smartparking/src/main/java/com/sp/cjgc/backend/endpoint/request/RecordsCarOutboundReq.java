package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * 记录-车辆出场记录表(RecordsCarOutbound)输入DTO
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
public class RecordsCarOutboundReq implements Serializable {
    private static final long serialVersionUID = -20778733639665414L;

    @Getter
    @Setter
    @ApiModel(value = "RecordsCarOutboundReq.QueryDTO", description = "记录-车辆出场记录表查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = -65737035210432887L;
        //@formatter:off
        @ApiModelProperty(value = "内地车牌号码", position = 1)
        private String mainlandLicensePlates;
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 2)
        private String otherLicensePlates;
        @ApiModelProperty(value = "进场时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 3)
        private String startTime1;
        @ApiModelProperty(value = "进场时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 4)
        private String endTime1;
        @ApiModelProperty(value = "出场时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 5)
        private String startTime2;
        @ApiModelProperty(value = "出场时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 6)
        private String endTime2;
        @ApiModelProperty(value = "车辆分组编码，1｜临保；2｜月保；3｜访客车；4｜无牌车；5|其他；", position = 7)
        private Integer carGroupId;
        @ApiModelProperty(value = "车辆分组名称", position = 8)
        private String carGroupName;
        //@formatter:on
    }
}
