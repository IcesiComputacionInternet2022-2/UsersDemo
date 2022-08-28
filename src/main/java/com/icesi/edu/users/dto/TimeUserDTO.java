package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class TimeUserDTO extends UserDTO {
    public String date;

    public TimeUserDTO() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.date = dtf.format(LocalDateTime.now());
    }
}
