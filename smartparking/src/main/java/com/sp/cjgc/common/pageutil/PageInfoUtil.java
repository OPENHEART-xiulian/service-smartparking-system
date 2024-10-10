package com.sp.cjgc.common.pageutil;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Zoey
 * @since 2023-07-06 13:47:55
 */
public class PageInfoUtil {

    /**
     * 计算页码
     *
     * @param dto
     */
    public static void pageReq(PageInfoQuery dto) {
        // 设置分页数据
        int pageNum = dto.getPageNumber() == null || dto.getPageNumber() == 0 ? 1 : dto.getPageNumber();
        int pageSize = dto.getPageSize() == null || dto.getPageSize() == 0 ? 5 : dto.getPageSize();
        int skipNum = (pageNum - 1) * pageSize;
        dto.setPageNumber(skipNum);
        dto.setPageSize(pageSize);
    }

    /**
     * 首页分页返回数据
     *
     * @param list  需要返回的数据集
     * @param dto   分页参数
     * @param total 总数
     * @return
     */
    public static PageInfoRespQuery pageResp(List list, PageInfoQuery dto, Long total) {
        // 创建返回值对象
        PageInfoRespQuery resp = new PageInfoRespQuery();
        // 计算页码
        int skipNum = dto.getPageNumber() / dto.getPageSize();
        // 页码
        resp.setPageNumber(skipNum + 1);
        resp.setPageSize(dto.getPageSize());
        // 判断是否有返回数据集
        if (CollectionUtil.isNotEmpty(list)) {
            // 当前页的数量
            resp.setSize(list.size());
            // 默认总页数为1页
            resp.setTotalNumber(dto.getPageNumber() + 1);
            // 计算总页数
            if (dto.getPageSize() > 0) {
                resp.setTotalNumber((Integer.valueOf(total.toString()) + dto.getPageSize() - 1) / dto.getPageSize());
            }
            // 总数量
            resp.setTotal(total);
            // 返回数据集合
            resp.setList(list);
        } else {
            // 全部设置为0
            resp.setSize(0);
            // 默认总页数
            resp.setTotalNumber(0);
            // 总数量
            resp.setTotal(0L);
            // 返回数据集合
            resp.setList(new ArrayList<>());
        }
        return resp;
    }
}

