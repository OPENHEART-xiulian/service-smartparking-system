package com.sp.cjgc.common.pageutil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author Zoey
 * @since 2023-07-06 13:47:52
 */
@Getter
@Setter
@NoArgsConstructor
public class PageInfoQuery implements Serializable {
    private static final long serialVersionUID = 968091769093927433L;

    // 页码
    private Integer pageNumber;
    // 每页显示数量  
    private Integer pageSize;
}

