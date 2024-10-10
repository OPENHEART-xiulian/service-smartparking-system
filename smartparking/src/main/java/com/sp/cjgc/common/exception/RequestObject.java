package com.sp.cjgc.common.exception;

import lombok.Data;

/**
 * @Author: Zoey
 * @Date: 2024/1/4
 * @Time: 上午9:44
 * @Describe:
 */
@Data
public class RequestObject<T> {

    T body;
}
