package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.security.SecurityContextHolder;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.icesi.edu.users.constant.UserErrorCode.CODE_01;

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
    public UserCreateDTO getUser(UUID userId) {
        return userMapper.fromSpecificUser(
                Stream.ofNullable(userService.getUser(userId))
                .peek(u -> {
                    if(!SecurityContextHolder.getContext().getUserId().equals(u.getId()))
                        throw new UserException(HttpStatus.UNAUTHORIZED, new UserError(CODE_01, CODE_01.getMessage()));
                }).findFirst().orElse(null));
    }

    @Override
    public UserDTO createUser(@Valid UserCreateDTO userDTO) {
        if(checkUserNull(userDTO))
            throw new RuntimeException();
        String email, phoneNumber, firstName, lastName;
        email = userDTO.getEmail();
        phoneNumber = userDTO.getPhoneNumber();
        if(!isNumberOrEmailPresent(email, phoneNumber))
            throw new RuntimeException();
        firstName = userDTO.getFirstName();
        lastName = userDTO.getLastName();
        System.out.println(firstName + " " + lastName );
        if(isValidEmail(email) &&
                isValidPhoneNumber(phoneNumber) &&
                isValidName(firstName) &&
                isValidName(lastName) &&
                checkNameLength(firstName) &&
                checkNameLength(lastName))
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    throw new RuntimeException();
    }

    private boolean checkUserNull(UserCreateDTO userDTO) {
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
        return usersWithHiddenId();
    }

    private List<UserDTO> mapUsersList() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private List<UserDTO> usersWithHiddenId() {
        List<UserDTO> users = mapUsersList();
        users.forEach(u -> {u.setHiddenId(hiddenId(u)); u.setId(null);});
        return users;
    }

    private String hiddenId(UserDTO user) {
        String currentId = String.valueOf(user.getId());
        return "..." + currentId.substring(currentId.length() - 4);
    }

}
