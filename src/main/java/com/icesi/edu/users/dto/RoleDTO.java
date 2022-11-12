package com.icesi.edu.users.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {

    private String name;

    private String description;

    private List<PermissionDTO> permissionDTOS;
}
