package com.sp.cjgc.common.pageutil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页响应参数
 *
 * @author Zoey
 * @since 2023-07-06 13:47:55
 */
@Getter
@Setter
@ApiModel(value = "PageInfoResp", description = "分页输出-输出DTO")
public class PageInfoResp implements Serializable {

    private static final long serialVersionUID = 515510308922855954L;

    @ApiModelProperty(value = "总数", position = 1)
    private Long total;

    @ApiModelProperty(value = "当前页", position = 2)
    private Integer pageNumber;

    @ApiModelProperty(value = "每页的数量", position = 3)
    private Integer pageSize;

    @ApiModelProperty(value = "当前页的数量", position = 4)
    private Integer size;

    @ApiModelProperty(value = "总页数", position = 5)
    private Integer totalNumber;
}

