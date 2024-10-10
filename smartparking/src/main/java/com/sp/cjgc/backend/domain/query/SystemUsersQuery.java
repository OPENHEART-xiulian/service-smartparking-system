package com.sp.cjgc.backend.domain.query;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 系统管理-用户表(SystemUsers)查询类
 *
 * @author zoey
 * @since 2024-08-13 15:25:09
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemUsersQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = -32057571249746010L;
    //@formatter:off
    private String id;                       //ID
    private String account;                  //登录账号
    private String password;                 //密码
    private String headSculpture;            //头像
    private String userName;                 //用户名称
    private LocalDateTime createTime;        //创建时间
    private Integer freeTime;                //免费时长，单位:分钟,默认免费停车15分钟
    private String phoneNumber;              //联系方式
    private String frontBusinessLicense;     //营业执照正面
    private String oppositeBusinessLicense;  //营业执照反面
    private Integer status;                  //状态，1|正常;2|锁定;3|禁用
    private String oldPassword;              //原密码
    //@formatter:on
}
