package com.sp.cjgc.backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * 系统管理-用户表(SystemUsers)实体类
 *
 * @author zoey
 * @since 2024-08-13 15:25:01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("system_users")
public class SystemUsers implements Serializable {
    private static final long serialVersionUID = -95763152313572240L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                       //ID
    @TableField(value = "account")
    private String account;                  //登录账号
    @TableField(value = "password")
    private String password;                 //密码
    @TableField(value = "head_sculpture")
    private String headSculpture;            //头像
    @TableField(value = "user_name")
    private String userName;                 //用户名称
    @TableField(value = "create_time")
    private LocalDateTime createTime;        //创建时间
    @TableField(value = "free_time")
    private Integer freeTime;                //免费时长，单位:分钟,默认免费停车15分钟
    @TableField(value = "phone_number")
    private String phoneNumber;              //联系方式
    @TableField(value = "front_business_license")
    private String frontBusinessLicense;     //营业执照正面
    @TableField(value = "opposite_business_license")
    private String oppositeBusinessLicense;  //营业执照反面
    @TableField(value = "status")
    private Integer status;                  //状态，1|正常;2|锁定;3|禁用
    @TableField(value = "unit_address")
    private String unitAddress;              //单位地址

    @TableField(exist = false)
    private String token;              //用户token
    @TableField(exist = false)
    private String roleId;             //角色ID
    @TableField(exist = false)
    private String roleName;           //角色名称
    @TableField(exist = false)
    private String permissions;        //用户权限,1|增，2|改，3|删，4|查
    //@formatter:on
}
