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
    private final int MAX_FIELD_LENGTH = 120;
    private final String NAME_REGEX = "^[a-zA-Z]+$";

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(checkUserNull(userDTO))
            throw new RuntimeException();
        String email, phoneNumber, firstName, lastName;
        email = userDTO.getEmail();
        phoneNumber = userDTO.getPhoneNumber();
        if(!isNumberOrEmailPresent(email, phoneNumber))
            throw new RuntimeException();
        firstName = userDTO.getFirstName();
        lastName = userDTO.getLastName();
        if(isValidEmail(email) && isValidPhoneNumber(phoneNumber) &&
                isValidName(firstName) && isValidName(lastName) &&
                checkNameLength(firstName) && checkNameLength(lastName))
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        throw new RuntimeException();
    }

    private boolean checkUserNull(UserDTO userDTO) {
        return userDTO == null;
    }

    private boolean isValidEmail(String email) {
        return (email == null || email.isBlank() || email.matches(EMAIL_REGEX));
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.matches(PHONE_NUMBER_REGEX));
    }

    private boolean isNumberOrEmailPresent(String email, String phoneNumber) {
        return (email != null && !email.isEmpty()) || (phoneNumber != null && !phoneNumber.isEmpty());
    }

    private boolean checkNameLength(String name) {
        return name.length() <= MAX_FIELD_LENGTH;
    }

    private boolean isValidName(String name) {
        return (name != null && name.matches(NAME_REGEX));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

}
