package com.icesi.edu.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;
<<<<<<< Updated upstream
import java.time.LocalTime;
=======
>>>>>>> Stashed changes
import java.util.UUID;


@Data
@Table(name = "`user`")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

<<<<<<< Updated upstream
   private LocalDate lastTimeSearched;
=======
    private String hashedPassword;

    private LocalDate lastTimeSearched;

>>>>>>> Stashed changes

    @PrePersist
    public void generateId(){
        this.id = UUID.randomUUID();
    }

}
