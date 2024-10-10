package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 记录-车辆进场记录表(RecordCarEnter)输入DTO
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
public class RecordCarEnterReq implements Serializable {
    private static final long serialVersionUID = -52301213055258847L;

    @Getter
    @Setter
    @ApiModel(value = "RecordCarEnterReq.QueryDTO", description = "记录-车辆进场记录表查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = -58764178515196858L;
        //@formatter:off
        @ApiModelProperty(value = "内地车牌号码", position = 1)
        private String mainlandLicensePlates;
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 2)
        private String otherLicensePlates;
        @ApiModelProperty(value = "创建时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 3)
        private String startTime1;
        @ApiModelProperty(value = "创建时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 4)
        private String endTime;
        //@formatter:on
    }
}
