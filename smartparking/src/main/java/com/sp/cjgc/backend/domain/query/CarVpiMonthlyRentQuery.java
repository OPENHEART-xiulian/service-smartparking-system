package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 车辆管理-vip月租车登记管理表(CarVpiMonthlyRent)查询类
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
@Getter
@Setter
@NoArgsConstructor
public class CarVpiMonthlyRentQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = 564208683115068580L;
    //@formatter:off
    private String id;                    //ID
    private String mainlandLicensePlates; //内地车牌号码
    private String otherLicensePlates;    //其他车牌号码，多个车牌号码英文逗号分隔
    private LocalDateTime createTime;     //登记时间
    private LocalDateTime updateTime;     //修改时间

    private Integer sort;                 //排序方式，1｜根据创建时间排序；2｜根据更新时间排序, 默认根据创建时间排序
    private Integer sortType;             //排序类型，1｜升序；2｜倒序, 默认倒序
    private String startTime;             //创建时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss
    private String endTime;               //创建时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss
    //@formatter:on
}
