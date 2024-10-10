package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.backend.domain.RecordsCarOutbound;
import com.sp.cjgc.backend.domain.query.RecordsCarOutboundQuery;
import com.sp.cjgc.backend.mapper.RecordsCarOutboundMapper;
import com.sp.cjgc.backend.service.RecordsCarOutboundService;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.pageutil.PageInfoUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 记录-车辆出场记录表(RecordsCarOutbound)表服务实现类
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
@Service
public class RecordsCarOutboundServiceImpl extends ServiceImpl<RecordsCarOutboundMapper, RecordsCarOutbound> implements RecordsCarOutboundService {

    /**
     * 记录-车辆出场记录表-分页查询列表
     *
     * @param query
     * @return
     */
    @Override
    public PageInfoRespQuery getPageList(RecordsCarOutboundQuery query) {
        // 赋值页码
        PageInfoUtil.pageReq(query);
        // 统计总数
        Long total = this.baseMapper.countTotal(query);
        // 查询列表
        List<RecordsCarOutbound> list = this.baseMapper.getPageList(query);
        // 返回分页数据
        return PageInfoUtil.pageResp(list, query, total);
    }
}
