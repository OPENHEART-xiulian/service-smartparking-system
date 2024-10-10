package com.sp.cjgc.backend.endpoint.response;

import com.sp.cjgc.common.pageutil.PageInfoResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统管理-角色表(SystemRole)输出DTO
 *
 * @author zoey
 * @since 2024-08-14 09:23:52
 */
public class SystemRoleResp implements Serializable {
    private static final long serialVersionUID = 906426840072649128L;

    @Getter
    @Setter
    @ApiModel(value = "SystemRoleResp.SystemRoleDTO", description = "系统管理-角色表输出DTO")
    public static class SystemRoleDTO implements Serializable {
        private static final long serialVersionUID = 362224592742026726L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;            
        @ApiModelProperty(value = "角色名称", position = 2)
        private String roleName;      
        @ApiModelProperty(value = "是否停用 1｜停用 0｜正常", position = 3)
        private Integer isDeactivate; 
        @ApiModelProperty(value = "显示顺序, 数值越小，排序越靠前", position = 4)
        private Integer roleSort;     
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemRoleResp.ListDTO", description = "系统管理-角色表-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<SystemRoleDTO> list;
    }
}
