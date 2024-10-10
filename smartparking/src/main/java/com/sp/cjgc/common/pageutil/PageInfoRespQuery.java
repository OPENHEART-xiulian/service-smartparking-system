package com.sp.cjgc.common.pageutil;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回参数
 *
 * @author Zoey
 * @since 2023-07-06 13:47:55
 */
@Getter
@Setter
public class PageInfoRespQuery implements Serializable {

    private static final long serialVersionUID = 515510308922855954L;

    private Long total;            // 总数
    private Integer pageNumber;    // 当前页
    private Integer pageSize;      // 每页的数量
    private Integer size;          // 当前页的数量
    private Integer totalNumber;   // 总页数
    private List list;             // 数据集
}

