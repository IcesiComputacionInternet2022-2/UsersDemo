package com.icesi.edu.users.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Table
@Entity
public class Role {

    @Id
    private UUID roleId;

    private String name;

    private String description;

    @ManyToMany
    private List<Permission> permissions;

}
