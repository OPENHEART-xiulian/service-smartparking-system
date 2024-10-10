package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统管理-车位数量明细表(SystemParkingDetail)查询类
 *
 * @author zoey
 * @since 2024-08-15 16:24:56
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemParkingDetailQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = 236635670551172445L;
    //@formatter:off
    private String id;               //ID
    private String userId;           //用户ID
    private Integer assignedNumber;  //租赁车位数量
    private String startTime;        //开始时间
    private String endTime;          //结束时间
    private String userName;         //用户名称
    private Integer assignedStatus;  //1｜在租；2｜过租
    //@formatter:on
}
