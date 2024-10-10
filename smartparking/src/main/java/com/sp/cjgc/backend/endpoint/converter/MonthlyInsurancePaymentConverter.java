package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.MonthlyInsurancePayment;
import com.sp.cjgc.backend.domain.query.MonthlyInsurancePaymentQuery;
import com.sp.cjgc.backend.endpoint.request.MonthlyInsurancePaymentReq;
import com.sp.cjgc.backend.endpoint.response.MonthlyInsurancePaymentResp;
import com.sp.cjgc.backend.enums.CarTypeEnum;
import com.sp.cjgc.backend.utils.DateTimeUtil;
import com.sp.cjgc.backend.utils.MonthlyUtil;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 月保-缴费记录(MonthlyInsurancePayment) 转换工具类
 *
 * @author zoey
 * @since 2024-08-16 13:23:23
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface MonthlyInsurancePaymentConverter {

    MonthlyInsurancePaymentConverter INSTANCE = Mappers.getMapper(MonthlyInsurancePaymentConverter.class);

    /**
     * 月保-缴费记录 数据新增或更新入参转换成 MonthlyInsurancePayment 对象
     *
     * @param dto
     * @return
     */
    default MonthlyInsurancePayment toEntity(MonthlyInsurancePaymentReq.CreateOrUpdateDTO dto) {
        MonthlyInsurancePayment entity = new MonthlyInsurancePayment();
        // 定义需要缴费金额
        String paymentAmount = "0";
        // 判断是否是长期缴费
        if (null != dto.getLongTerm() && 1 == dto.getLongTerm()) {
            // 计算长期费用
            paymentAmount = MonthlyUtil.countLongTerm(dto.getMonthlyFree());
        } else {
            if (StringUtils.isBlank(dto.getMonthlyStartTime()))
                throw new BizException(RespEnum.FAILURE.getCode(), "开始时间不能为空");
            if (StringUtils.isBlank(dto.getMonthlyEndTime()))
                throw new BizException(RespEnum.FAILURE.getCode(), "结束时间不能为空");
            if (StringUtils.isBlank(dto.getMainlandLicensePlates()))
                throw new BizException(RespEnum.FAILURE.getCode(), "内地车牌号码不能为空");
            if (StringUtils.isBlank(dto.getCarTypeCode()))
                throw new BizException(RespEnum.FAILURE.getCode(), "车辆类型编码不能为空");
            if (StringUtils.isBlank(dto.getMonthlyFree()))
                throw new BizException(RespEnum.FAILURE.getCode(), "月保费用不能为空");
            // 补全时间
            entity.setMonthlyStartTime(DateTimeUtil.timeUtils(dto.getMonthlyStartTime(), 1));
            entity.setMonthlyEndTime(DateTimeUtil.timeUtils(dto.getMonthlyEndTime(), 2));
            // 计算需要缴纳的月保费用
            paymentAmount = MonthlyUtil.countFree(dto.getCarTypeCode(), dto.getMonthlyFree(),
                    entity.getMonthlyStartTime(), entity.getMonthlyEndTime());
        }
        entity.setPaymentAmount(paymentAmount);
        entity.setAccumulatePaymentAmount(paymentAmount);
        // 未缴费
        entity.setPaymentStatus(0);
        entity.setId(dto.getId());
        entity.setMainlandLicensePlates(dto.getMainlandLicensePlates());
        entity.setCarTypeCode(dto.getCarTypeCode());
        entity.setCarTypeName(CarTypeEnum.getName(dto.getCarTypeCode()));
        entity.setMonthlyFree(dto.getMonthlyFree());
        entity.setLongTerm(dto.getLongTerm());
        entity.setParkingLotCode(dto.getParkingLotCode());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setUserName(dto.getUserName());
        entity.setCardId(dto.getCardId());
        return entity;
    }

    /**
     * 月保-缴费记录 查询条件入参转换成 MonthlyInsurancePaymentQuery 对象
     *
     * @param queryDTO
     * @return
     */
    MonthlyInsurancePaymentQuery toQuery(MonthlyInsurancePaymentReq.QueryDTO queryDTO);

    /**
     * 月保-缴费记录 对象响应数据转换
     *
     * @param entity
     * @return
     */
    MonthlyInsurancePaymentResp.MonthlyInsurancePaymentDTO toDTO(MonthlyInsurancePayment entity);

    /**
     * 月保-缴费记录 列表响应数据转换
     *
     * @param list
     * @return
     */
    List<MonthlyInsurancePaymentResp.MonthlyInsurancePaymentDTO> toListDTO(List<MonthlyInsurancePayment> list);

    /**
     * 分页查询 月保-缴费记录 列表响应数据转换
     *
     * @param query
     * @return
     */
    MonthlyInsurancePaymentResp.ListDTO toPage(PageInfoRespQuery query);
}
