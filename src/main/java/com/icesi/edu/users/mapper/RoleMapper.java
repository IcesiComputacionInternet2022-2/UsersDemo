package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.RoleDTO;
import com.icesi.edu.users.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role fromDTO(RoleDTO roleDTO);

    RoleDTO fromRole(Role role);

}
