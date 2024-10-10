package com.sp.cjgc.backend.endpoint.response;

import java.time.LocalDateTime;

import com.sp.cjgc.common.pageutil.PageInfoResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

/**
 * 系统管理-用户表(SystemUsers)输出DTO
 *
 * @author zoey
 * @since 2024-08-13 15:25:16
 */
public class SystemUsersResp implements Serializable {
    private static final long serialVersionUID = -81199109561205956L;
    
    @Getter
    @Setter
    @ApiModel(value = "SystemUsersResp.SystemUsersDTO", description = "系统管理-用户表输出DTO")
    public static class SystemUsersDTO implements Serializable {
        private static final long serialVersionUID = 187600570726433387L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                
        @ApiModelProperty(value = "登录账号", position = 2)
        private String account;           
        @ApiModelProperty(value = "密码", position = 3)
        private String password;          
        @ApiModelProperty(value = "头像", position = 4)
        private String headSculpture;     
        @ApiModelProperty(value = "用户名称", position = 5)
        private String userName;          
        @ApiModelProperty(value = "创建时间", position = 6)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime; 
        @ApiModelProperty(value = "免费时长，单位:分钟,默认免费停车15分钟", position = 7)
        private Integer freeTime;
        @ApiModelProperty(value = "用户角色ID", position = 8)
        private String roleId;
        @ApiModelProperty(value = "用户角色", position = 9)
        private String roleName;
        @ApiModelProperty(value = "登陆token", position = 10)
        private String token;
        @ApiModelProperty(value = "联系方式", position = 11)
        private String phoneNumber;
        @ApiModelProperty(value = "营业执照正面", position = 12)
        private String frontBusinessLicense;
        @ApiModelProperty(value = "营业执照反面", position = 13)
        private String oppositeBusinessLicense;
        @ApiModelProperty(value = "状态，1|正常;2|锁定;3|禁用", position = 14)
        private Integer status;
        @ApiModelProperty(value = "单位地址", position = 15)
        private String unitAddress;
        //@formatter:on
    }
    
    @Getter
    @Setter
    @ApiModel(value = "SystemUsersResp.ListDTO", description = "系统管理-用户表-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<SystemUsersDTO> list;
    }
}
