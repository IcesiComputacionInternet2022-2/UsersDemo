package com.icesi.edu.users.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PermissionDTO {


    private String uri;

    private String permissionKey;

    private String method;

}
