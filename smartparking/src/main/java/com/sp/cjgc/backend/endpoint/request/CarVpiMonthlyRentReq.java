package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 车辆管理-vip月租车登记管理表(CarVpiMonthlyRent)输入DTO
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
public class CarVpiMonthlyRentReq implements Serializable {
    private static final long serialVersionUID = -24599605854870981L;

    @Getter
    @Setter
    @ApiModel(value = "CarVpiMonthlyRentReq.CreateOrUpdateDTO", description = "车辆管理-vip月租车登记管理表新增或修改DTO")
    public static class CreateOrUpdateDTO implements Serializable {
        private static final long serialVersionUID = 303840512429333818L;
        //@formatter:off
        @ApiModelProperty(value = "ID，新增不传，更新必传", position = 1, required = true)
        private String id;
        @ApiModelProperty(value = "内地车牌号码", position = 2, required = true)
        private String mainlandLicensePlates;
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 3)
        private String otherLicensePlates;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "CarVpiMonthlyRentReq.DeleteDTO", description = "车辆管理-vip月租车登记管理删除DTO")
    public static class DeleteDTO implements Serializable {
        private static final long serialVersionUID = -35293709870447150L;
        //@formatter:off
        @ApiModelProperty(value = "ids", position = 1, required = true)
        private List<String> ids;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "CarVpiMonthlyRentReq.QueryDTO", description = "车辆管理-vip月租车登记管理表查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = -66552280977889279L;
        //@formatter:off
        @ApiModelProperty(value = "内地车牌号码", position = 1)
        private String mainlandLicensePlates;
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 2)
        private String otherLicensePlates;
        @ApiModelProperty(value = "排序方式，1｜根据创建时间排序；2｜根据更新时间排序, 默认根据创建时间排序", position = 3)
        private Integer sort;
        @ApiModelProperty(value = "排序类型，1｜升序；2｜倒序, 默认倒序", position = 4)
        private Integer sortType;
        @ApiModelProperty(value = "创建时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 5)
        private String startTime;
        @ApiModelProperty(value = "创建时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 6)
        private String endTime;
        //@formatter:on
    }
}
