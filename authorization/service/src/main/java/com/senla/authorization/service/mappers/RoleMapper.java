package com.senla.authorization.service.mappers;

import com.senla.authorization.dto.RoleDto;
import com.senla.authorization.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto mapToDto(Role role);
    Role mapToModel(RoleDto roleDto);

    void updateRole(RoleDto roleDto, @MappingTarget Role role);

}
