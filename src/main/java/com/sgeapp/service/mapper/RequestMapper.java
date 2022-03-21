package com.sgeapp.service.mapper;

import com.sgeapp.domain.Request;
import com.sgeapp.service.dto.RequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicationUserMapper.class, CampaignMapper.class })
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "id")
    @Mapping(target = "compaign", source = "compaign", qualifiedByName = "id")
    RequestDTO toDto(Request s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDTO toDtoId(Request request);
}
