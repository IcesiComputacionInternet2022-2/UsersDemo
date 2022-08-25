package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {


    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    public boolean emailDomain(String email){
        boolean domain = false;
        String[] newStr =  email.split("@");
        if(newStr[0].matches("[^\\w]") && newStr[1].equals("icesi.edu.co")){
            domain = true;
        }
        return domain;
    }
    public boolean phoneValidation(String phone) {
        boolean isNumber = false;
        int contador = 0;
        for (int i = 0; i < phone.length(); i++){
            if (phone.charAt(i) == ' ') {
                contador++;
            }
        }

        if(phone.startsWith("+57") && (phone.length() == 13) && (contador != 0)){
            isNumber = true;
        }
        return isNumber;
    }
    public boolean firstNamelength(String firstName){
        return(firstName.length() <= 120);
    }
    public boolean lastNamelength(String lastName){
        return(lastName.length() <= 120);
    }


}
