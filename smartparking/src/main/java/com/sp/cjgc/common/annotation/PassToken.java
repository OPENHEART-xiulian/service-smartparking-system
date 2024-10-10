package com.sp.cjgc.common.annotation;

import com.sp.cjgc.common.utils.AuthorityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Zoey
 * @Date: 2023/6/26
 * @Time: 下午2:52
 * @Describe: true不需要验证 ｜ false需要验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {

    /**
     * 是否需要验证apiKey，默认不需要
     *
     * @return
     */
    boolean required() default true;

    /**
     * 是否进行增删查改接口调用权限验证，
     * true则进行权限验证，false则不验证
     * 默认需要权限验证
     *
     * @return
     */
    boolean verifyPermissions() default true;

    /**
     * 增删查改接口调用权限，默认只有查询权限
     *
     * @return
     */
    String authority() default AuthorityType.READ;
}
