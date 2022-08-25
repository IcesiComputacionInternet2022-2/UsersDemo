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
    private boolean validateEmail(String email){
        return email.matches("\\w+@icesi.edu.co$"); //Domain and no special characters
    }
    private boolean validateNumber(String phoneNumber){
        return phoneNumber.matches("^(\\+57)[0-9]{10}"); //+57 and 10 numbers
    }
    private boolean validateNameAndLastname(String nameOrLastname){
        return nameOrLastname.matches("[aA-zZ ]{0,120}"); //More than 0 lesser than 120
    }
}
