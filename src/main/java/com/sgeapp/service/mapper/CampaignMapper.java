package com.sgeapp.service.mapper;

import com.sgeapp.domain.Campaign;
import com.sgeapp.service.dto.CampaignDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campaign} and its DTO {@link CampaignDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicationUserMapper.class })
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {
    @Mapping(target = "admin", source = "admin", qualifiedByName = "id")
    CampaignDTO toDto(Campaign s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoId(Campaign campaign);
}
