package com.icesi.edu.users.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table
@Entity
@Data
public class Permission {

    @Id
    private UUID permissionId;

    private String uri;

    private String key;

}
