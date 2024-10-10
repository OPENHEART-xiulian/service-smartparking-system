package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统管理-用户表(SystemUsers)输入DTO
 *
 * @author zoey
 * @since 2024-08-13 15:25:15
 */
public class SystemUsersReq implements Serializable {
    private static final long serialVersionUID = -73433651604941136L;

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.CreateOrUpdateDTO", description = "系统管理-用户表新增或编辑DTO")
    public static class CreateOrUpdateDTO implements Serializable {
        private static final long serialVersionUID = -39275166386764622L;
        //@formatter:off
        @ApiModelProperty(value = "用户ID,新增不传，编辑必传", position = 1)
        private String id;
        @ApiModelProperty(value = "登录账号", position = 2, required = true)
        private String account;
        @ApiModelProperty(value = "头像", position = 4)
        private String headSculpture;
        @ApiModelProperty(value = "用户名称", position = 5)
        private String userName;
        @ApiModelProperty(value = "免费时长，单位:分钟", position = 6, required = true)
        private Integer freeTime;
        @ApiModelProperty(value = "角色ID", position = 7, required = true)
        private String roleId;
        @ApiModelProperty(value = "联系方式", position = 8)
        private String phoneNumber;
        @ApiModelProperty(value = "营业执照正面", position = 9)
        private String frontBusinessLicense;
        @ApiModelProperty(value = "营业执照反面", position = 10)
        private String oppositeBusinessLicense;
        @ApiModelProperty(value = "状态，1|正常;2|锁定;3|禁用", position = 11)
        private Integer status;
        @ApiModelProperty(value = "单位地址", position = 12)
        private String unitAddress;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.LoginDTO", description = "用户账号密码登录-DTO")
    public static class LoginDTO implements Serializable {
        private static final long serialVersionUID = -96983650065296784L;
        //@formatter:off
        @ApiModelProperty(value = "登录账号", position = 1, required = true)
        private String account;
        @ApiModelProperty(value = "密码", position = 2, required = true)
        private String password;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.UpdatePasswordDTO", description = "用户修改密码-DTO")
    public static class UpdatePasswordDTO implements Serializable {
        private static final long serialVersionUID = -96983650065296784L;
        //@formatter:off
        @ApiModelProperty(value = "登录账号", position = 1, required = true)
        private String account;
        @ApiModelProperty(value = "原密码", position = 2, required = true)
        private String oldPassword;
        @ApiModelProperty(value = "新密码", position = 3, required = true)
        private String password;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.UpdateDTO", description = "用户表编辑DTO")
    public static class UpdateDTO implements Serializable {
        private static final long serialVersionUID = 414714144378933291L;
        //@formatter:off
        @ApiModelProperty(value = "用户ID", position = 1, required = true)
        private String id;
        @ApiModelProperty(value = "头像", position = 3)
        private String headSculpture;
        @ApiModelProperty(value = "用户名称", position = 4)
        private String userName;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.QueryDTO", description = "系统管理-用户表查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = -56680528002089283L;
        //@formatter:off
        @ApiModelProperty(value = "用户名称", position = 1)
        private String userName;
        @ApiModelProperty(value = "登录账号", position = 2)
        private String account;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.DeleteDTO", description = "系统管理-用户表删除DTO")
    public static class DeleteDTO implements Serializable {
        private static final long serialVersionUID = -35293709870447150L;
        //@formatter:off
        @ApiModelProperty(value = "ids", position = 1, required = true)
        private List<String> ids;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.ResettingPasswordDTO", description = "系统管理-用户重置密码DTO")
    public static class ResettingPasswordDTO implements Serializable {
        private static final long serialVersionUID = -35293709870447150L;
        //@formatter:off
        @ApiModelProperty(value = "ids", position = 1, required = true)
        private List<String> ids;
        @ApiModelProperty(value = "新密码", position = 2, required = true)
        private String password;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemUsersReq.UpdateStatusDTO", description = "系统管理-用户更改状态DTO")
    public static class UpdateStatusDTO implements Serializable {
        private static final long serialVersionUID = -35293709870447150L;
        //@formatter:off
        @ApiModelProperty(value = "ids", position = 1, required = true)
        private List<String> ids;
        @ApiModelProperty(value = "状态，1|正常;2|锁定;3|禁用", position = 2, required = true)
        private Integer status;
        //@formatter:on
    }
}
