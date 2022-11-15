package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.PermissionDTO;
import com.icesi.edu.users.model.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission fromDTO(PermissionDTO permissionDTO);

    PermissionDTO fromPermission(Permission permission);

    List<Permission> fromListDTO(List<PermissionDTO> permissionDTOList);

    List<PermissionDTO> fromListPermission(List<Permission> permissionList);

}
