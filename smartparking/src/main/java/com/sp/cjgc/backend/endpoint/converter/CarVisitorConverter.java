package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.CarVisitor;
import com.sp.cjgc.backend.domain.query.CarVisitorQuery;
import com.sp.cjgc.backend.endpoint.request.CarVisitorReq;
import com.sp.cjgc.backend.endpoint.response.CarVisitorResp;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 车辆管理-访客车登记管理表(CarVisitor) 转换工具类
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface CarVisitorConverter {

    CarVisitorConverter INSTANCE = Mappers.getMapper(CarVisitorConverter.class);

    /**
     * 车辆管理-访客车登记管理表 数据新增入参转换成 SpVisitorVans 对象
     *
     * @param dto
     * @return
     */
    default CarVisitor toEntity(CarVisitorReq.CreateOrUpdateDTO dto) {
        if (StringUtils.isBlank(dto.getStartTime()))
            throw new BizException(RespEnum.FAILURE.getCode(), "开始时间不能为空");
        if (StringUtils.isBlank(dto.getEndTime()))
            throw new BizException(RespEnum.FAILURE.getCode(), "结束时间不能为空");
        if (StringUtils.isBlank(dto.getMainlandLicensePlates()))
            throw new BizException(RespEnum.FAILURE.getCode(), "内地车牌号码不能为空");
        CarVisitor spVisitorVans = new CarVisitor();

        spVisitorVans.setId(dto.getId());
        spVisitorVans.setMainlandLicensePlates(dto.getMainlandLicensePlates());
        spVisitorVans.setOtherLicensePlates(dto.getOtherLicensePlates());
        LocalDateTime now = LocalDateTime.now();
        if (StringUtils.isBlank(dto.getId())) {
            spVisitorVans.setCreateTime(now);
            spVisitorVans.setUpdateTime(now);
        } else {
            spVisitorVans.setUpdateTime(now);
        }
        // 定义日期时间的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        spVisitorVans.setStartTime(LocalDateTime.parse(dto.getStartTime(), formatter));
        spVisitorVans.setEndTime(LocalDateTime.parse(dto.getEndTime(), formatter));

        return spVisitorVans;
    }

    /**
     * 车辆管理-访客车登记管理表 查询条件入参转换成 CarVisitorQuery 对象
     *
     * @param queryDTO
     * @return
     */
    default CarVisitorQuery toQuery(CarVisitorReq.QueryDTO queryDTO) {
        if (queryDTO == null) {
            return null;
        }

        CarVisitorQuery carVisitorQuery = new CarVisitorQuery();

        carVisitorQuery.setPageNumber(queryDTO.getPageNumber());
        carVisitorQuery.setPageSize(queryDTO.getPageSize());
        carVisitorQuery.setMainlandLicensePlates(queryDTO.getMainlandLicensePlates());
        carVisitorQuery.setOtherLicensePlates(queryDTO.getOtherLicensePlates());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(queryDTO.getStartTime())) {
            carVisitorQuery.setStartTime(LocalDateTime.parse(queryDTO.getStartTime(), formatter));
        }
        if (StringUtils.isNotBlank(queryDTO.getEndTime())) {
            carVisitorQuery.setEndTime(LocalDateTime.parse(queryDTO.getEndTime(), formatter));
        }
        carVisitorQuery.setSort(queryDTO.getSort());
        carVisitorQuery.setSortType(queryDTO.getSortType());
        carVisitorQuery.setIsFree(queryDTO.getIsFree());

        return carVisitorQuery;
    }

    /**
     * 车辆管理-访客车登记管理表 对象响应数据转换
     *
     * @param entity
     * @return
     */
    CarVisitorResp.CarVisitorDTO toDTO(CarVisitor entity);

    /**
     * 车辆管理-访客车登记管理表 列表响应数据转换
     *
     * @param list
     * @return
     */
    List<CarVisitorResp.CarVisitorDTO> toListDTO(List<CarVisitor> list);

    /**
     * 分页查询 车辆管理-访客车登记管理表 列表响应数据转换
     *
     * @param query
     * @return
     */
    CarVisitorResp.ListDTO toPage(PageInfoRespQuery query);
}
