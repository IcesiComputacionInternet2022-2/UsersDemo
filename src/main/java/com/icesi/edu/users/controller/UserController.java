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
        if (validateUser(userDTO))
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        throw new RuntimeException();
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateUser(UserDTO userDTO) {
        return validateEmail(userDTO.getEmail()) && validatePhoneNumber(userDTO.getEmail()) && (!userDTO.getEmail().isEmpty() || !userDTO.getPhoneNumber().isEmpty() && validateFirstLastNames(userDTO.getFirstName(), userDTO.getLastName()));
    }

    private boolean validateEmail(String userEmail) {
        return userEmail.matches("\\w+@icesi.edu.co$");
    }

    private boolean validatePhoneNumber(String userPhoneNumber) {
        return userPhoneNumber.matches("^\\+57\\d{10}");
    }

    private boolean validateFirstLastNames(String firstName, String lastName) {
        return firstName.matches("[a-zA-Z]{0,120}") && lastName.matches("[a-zA-Z]{0,120}");
    }
}