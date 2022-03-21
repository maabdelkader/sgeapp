package com.sgeapp.service.mapper;

import com.sgeapp.domain.TimeSheet;
import com.sgeapp.service.dto.TimeSheetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeSheet} and its DTO {@link TimeSheetDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestMapper.class, CompanyMapper.class })
public interface TimeSheetMapper extends EntityMapper<TimeSheetDTO, TimeSheet> {
    @Mapping(target = "request", source = "request", qualifiedByName = "id")
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    TimeSheetDTO toDto(TimeSheet s);
}
