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

    private final String EMAIL_REGEX = "^[A-Za-z0-9]+@icesi\\.edu\\.co$";
    private final String PHONE_NUMBER_REGEX = "^\\+57[0-9]{10}$";

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phoneNumber = userDTO.getPhoneNumber();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    private boolean checkUserNull(UserDTO userDTO) {
        return userDTO == null;
    }

    private boolean isValidEmail(String email) {
        if(email.matches(EMAIL_REGEX))
            return true;
        throw new RuntimeException();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if(phoneNumber.matches(PHONE_NUMBER_REGEX))
            return true;
        throw new RuntimeException();
    }

    private boolean isNumberOrEmailPresent(String email, String phoneNumber) {
        if(email != null || !email.isEmpty() || phoneNumber != null || !phoneNumber.isEmpty())
            return true;
        throw new RuntimeException();
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
