package com.sgeapp.service.mapper;

import com.sgeapp.domain.ApplicationUser;
import com.sgeapp.service.dto.ApplicationUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CompanyMapper.class })
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "id")
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    ApplicationUserDTO toDto(ApplicationUser s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoId(ApplicationUser applicationUser);
}
