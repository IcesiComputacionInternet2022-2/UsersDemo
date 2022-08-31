package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        validateUser(userDTO);
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private void validateUser(UserDTO userDTO) {
        if (userDTO.getEmail().isEmpty() && userDTO.getPhoneNumber().isEmpty()) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("01", ErrorConstants.CODE_UD_01));
        }
        boolean validateEmail = userDTO.getEmail().isEmpty() || validateEmail(userDTO.getEmail());
        boolean validatePhoneNumber = userDTO.getPhoneNumber().isEmpty() || validatePhoneNumber(userDTO.getPhoneNumber());
        if (validateEmail && validatePhoneNumber)
            validateNames(userDTO.getFirstName(), userDTO.getLastName());
    }

    private boolean validateEmail(String userEmail) {
        if (!userEmail.matches("\\w+@icesi\\.edu\\.co$"))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("02", ErrorConstants.CODE_UD_02));
        return true;
    }

    private boolean validatePhoneNumber(String userPhoneNumber) {
        if (!userPhoneNumber.matches("^\\+57\\d{10}"))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("03", ErrorConstants.CODE_UD_03));
        return true;
    }

    private void validateNames(String firstName, String lastName) {
        if (!firstName.matches("[a-zA-Z]{0,120}") || !lastName.matches("[a-zA-Z]{0,120}"))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("04", ErrorConstants.CODE_UD_04));
    }
}