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
        if (validateEmail(userDTO.getEmail()) && validatePhone(userDTO.getPhoneNumber()))
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        else throw new RuntimeException("Correo invalido");
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
    private boolean validateEmail(String email){
        String user = email.split("@")[0];
        String domain = email.split("@")[1];
        if (domain.equals("icesi.edu.co") && user.matches("^[0-9a-zA-Z]+$"))
            return true;
        else return false;
    }
    private boolean validatePhone(String phone){
        if (phone.substring(0,2).equals("+57") && phone.matches("^[0-9]+$")&&phone.length()==10)
            return true;
        else return false;
    }

    private boolean validateCamps(String email,String phone){
        if (email.matches("^[0-9a-zA-Z]+$"))
            if (phone.matches("^[0-9a-zA-Z]+$"))
                return true;
        else return false;
    }
}
