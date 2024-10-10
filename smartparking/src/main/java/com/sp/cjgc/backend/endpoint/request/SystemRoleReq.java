package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统管理-角色表(SystemRole)输入DTO
 *
 * @author zoey
 * @since 2024-08-14 09:23:51
 */
public class SystemRoleReq implements Serializable {
    private static final long serialVersionUID = -32700193819546787L;

    @Getter
    @Setter
    @ApiModel(value = "SystemRoleReq.CreateOrUpdateDTO", description = "系统管理-角色表新增或修改DTO")
    public static class CreateOrUpdateDTO implements Serializable {
        private static final long serialVersionUID = -28916317545324157L;
        //@formatter:off
        @ApiModelProperty(value = "角色ID，新增不传，更新必传", position = 1)
        private String id;            
        @ApiModelProperty(value = "角色名称", position = 2)
        private String roleName;      
        @ApiModelProperty(value = "是否停用 1｜停用 0｜正常", position = 3)
        private Integer isDeactivate; 
        @ApiModelProperty(value = "显示顺序, 数值越小，排序越靠前", position = 4, required = true)
        private Integer roleSort;
        @ApiModelProperty(value = "角色权限,1|增，2｜改，3｜删，4｜查;多个权限以逗号拼接", position = 5, required = true)
        private String permissions;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemRoleReq.QueryDTO", description = "系统管理-角色表查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = 332860804088587346L;
        //@formatter:off
        @ApiModelProperty(value = "角色名称", position = 1)
        private String roleName;
        //@formatter:on
    }
}
