package com.senla.authorization.service.mappers;

import com.senla.authorization.dto.AuthorityDto;
import com.senla.authorization.model.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    AuthorityDto mapToDto(Authority authority);
    Authority mapToModel(AuthorityDto authorityDto);
    void updateModel(AuthorityDto authorityDto, @MappingTarget Authority authority);

}
