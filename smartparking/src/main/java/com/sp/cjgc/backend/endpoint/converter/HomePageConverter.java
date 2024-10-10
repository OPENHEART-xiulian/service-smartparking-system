package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.MonthlyStatistics;
import com.sp.cjgc.backend.domain.ParkingSpaceStatistics;
import com.sp.cjgc.backend.domain.RevenueStatistics;
import com.sp.cjgc.backend.endpoint.response.HomePageResp;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 09:42:15
 * @Description:
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface HomePageConverter {

    HomePageConverter INSTANCE = Mappers.getMapper(HomePageConverter.class);

    HomePageResp.CountOrderDTO toDto(MonthlyStatistics entity);

    List<HomePageResp.CountOrderDTO> toListDto(List<MonthlyStatistics> list);

    HomePageResp.RevenueStatisticsDTO toDto(RevenueStatistics entity);

    HomePageResp.ParkingSpaceStatisticsDTO toDto(ParkingSpaceStatistics entity);
}
