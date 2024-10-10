package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.CarVpiMonthlyRent;
import com.sp.cjgc.backend.domain.query.CarVpiMonthlyRentQuery;
import com.sp.cjgc.backend.endpoint.request.CarVpiMonthlyRentReq;
import com.sp.cjgc.backend.endpoint.response.CarVpiMonthlyRentResp;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 车辆管理-vip月租车登记管理表(CarVpiMonthlyRent) 转换工具类
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface CarVpiMonthlyRentConverter {

    CarVpiMonthlyRentConverter INSTANCE = Mappers.getMapper(CarVpiMonthlyRentConverter.class);
    
    /**
     * 车辆管理-vip月租车登记管理表 数据新增或更新入参转换成 CarVpiMonthlyRent 对象
     *
     * @param dto
     * @return
     */
    CarVpiMonthlyRent toEntity(CarVpiMonthlyRentReq.CreateOrUpdateDTO dto);

    /**
     * 车辆管理-vip月租车登记管理表 查询条件入参转换成 CarVpiMonthlyRentQuery 对象
     *
     * @param queryDTO
     * @return
     */
    CarVpiMonthlyRentQuery toQuery(CarVpiMonthlyRentReq.QueryDTO queryDTO);
    
    /**
     * 车辆管理-vip月租车登记管理表 对象响应数据转换
     *
     * @param entity
     * @return
     */
    CarVpiMonthlyRentResp.CarVpiMonthlyRentDTO toDTO(CarVpiMonthlyRent entity);
    
    /**
     * 车辆管理-vip月租车登记管理表 列表响应数据转换
     *
     * @param list
     * @return
     */
    List<CarVpiMonthlyRentResp.CarVpiMonthlyRentDTO> toListDTO(List<CarVpiMonthlyRent> list);
    
    /**
     * 分页查询 车辆管理-vip月租车登记管理表 列表响应数据转换
     *
     * @param query
     * @return
     */
    CarVpiMonthlyRentResp.ListDTO toPage(PageInfoRespQuery query);
}
