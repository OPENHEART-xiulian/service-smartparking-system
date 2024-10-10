package com.sp.cjgc.backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * 系统管理-角色表(SystemRole)实体类
 *
 * @author zoey
 * @since 2024-08-13 15:25:27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("system_role")
public class SystemRole implements Serializable {
    private static final long serialVersionUID = 834569496120568557L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;           //ID
    @TableField(value = "role_name")
    private String roleName;     //角色名称
    @TableField(value = "is_deactivate")
    private String isDeactivate; //是否停用 1｜停用 0｜正常
    @TableField(value = "role_sort")
    private Integer roleSort;    //显示顺序, 数值越小，排序越靠前
    @TableField(value = "permissions")
    private String permissions;  //角色权限,1|增，2｜改，3｜删，4｜查;多个权限以逗号拼接
    //@formatter:on
}
