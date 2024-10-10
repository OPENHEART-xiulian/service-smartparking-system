package com.sp.cjgc.common.exception;

/**
 * @Author: Zoey
 * @Date: 2024/1/4
 * @Time: 上午9:42
 * @Describe:
 */
public interface BaseErrorInfoInterface {

    /**
     * 错误码
     *
     * @return
     */
    Integer getResultCode();

    /**
     * 错误描述
     *
     * @return
     */
    String getResultMsg();
}
