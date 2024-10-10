package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 系统管理-角色表(SystemRole)查询类
 *
 * @author zoey
 * @since 2024-08-13 15:25:27
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemRoleQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = 431223193526224783L;
    //@formatter:off
    private String id;           //ID
    private String roleName;     //角色名称
    private Object isDeactivate; //是否停用 1｜停用 0｜正常
    private Integer roleSort;    //显示顺序, 数值越小，排序越靠前
    private String permissions;  //角色权限,1|增，2｜改，3｜删，4｜查;多个权限以逗号拼接
    //@formatter:on
}
