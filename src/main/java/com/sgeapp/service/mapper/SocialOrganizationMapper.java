package com.sgeapp.service.mapper;

import com.sgeapp.domain.SocialOrganization;
import com.sgeapp.service.dto.SocialOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocialOrganization} and its DTO {@link SocialOrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SocialOrganizationMapper extends EntityMapper<SocialOrganizationDTO, SocialOrganization> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocialOrganizationDTO toDtoId(SocialOrganization socialOrganization);
}
