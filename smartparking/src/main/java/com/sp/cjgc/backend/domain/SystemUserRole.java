package com.sp.cjgc.backend.domain;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * 系统管理-用户和角色关联表(SystemUserRole)实体类
 *
 * @author zoey
 * @since 2024-08-13 15:25:27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "userId")
@TableName("system_user_role")
public class SystemUserRole implements Serializable {
    private static final long serialVersionUID = 204717901233154491L;
    //@formatter:off
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private String userId;            //用户ID
    @TableField(value = "role_id")
    private String roleId;            //角色ID
    @TableField(value = "create_time")
    private LocalDateTime createTime; //创建时间
    //@formatter:on
}
