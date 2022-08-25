package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
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

    private boolean validateEmailOrPhone(UserDTO userDTO){
        return userDTO.getEmail() != null && userDTO.getPhoneNumber() != null;
    }

    private boolean validateEmailDomain(String email){
        return email !=null && email.matches("@icesi\\.edu\\.co$");
    }

    private boolean validateEmailUsername(String email){
        return email.matches("^[a-zA-Z0-9._-]+@");
    }

    private boolean validatePhoneNumber(String phone){
        return phone != null && phone.matches("^\\+57[0-9]{10}$");
    }
    private boolean validateFirstNameLength(String firstName){
        return false;
    }

    private boolean validateLastNameLength(String lastName){
        return false;
    }








}
