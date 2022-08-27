package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
/*
* A new DTO class to add the extra attribute "date" only when a specific user is fetched
* */
public class UserTimeDTO extends UserDTO{
    private String localTime;

    public  UserTimeDTO(){
        //Getting and formatting the current requested time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.localTime = dtf.format(LocalDateTime.now());
    }
}



