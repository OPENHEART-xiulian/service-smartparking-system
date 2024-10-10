package com.sp.cjgc.common.pageutil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author Zoey
 * @since 2023-07-06 13:47:55
 */
@Getter
@Setter
@ApiModel(value = "PageInfoReq", description = "分页参数-输入DTO")
public class PageInfoReq implements Serializable {

    private static final long serialVersionUID = 217485444309430842L;

    @ApiModelProperty(value = "页码，默认为 1", position = 1)
    private Integer pageNumber;

    @ApiModelProperty(value = "每页显示数量，默认为 5", position = 2)
    private Integer pageSize;
}

