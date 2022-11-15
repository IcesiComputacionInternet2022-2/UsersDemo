package com.icesi.edu.users.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "user_permission")
@Entity
@Data
public class Permission {

    @Id
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID permissionId;

    private String uri;

    private String permissionKey;

    @Column(name = "permission_method")
    private String method;


}
