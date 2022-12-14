package com.icesi.edu.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@Data
@Table(name = "`user`")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @PrePersist
    public void generateId(){
        this.id = UUID.randomUUID();
    }


}
