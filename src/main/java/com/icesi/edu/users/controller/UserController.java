package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    private final String domain = "@icei.edu.co";

    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();
        String firstname = userDTO.getFirstName();
        String lastname = userDTO.getLastName();
        if (isEmailCorrect(email) && isPhoneCorrect(phone) && isFirstnameCorrect(firstname) && isLastnameCorrect(lastname))
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        else
            throw new RuntimeException();
    }

    //Email Validation
    private boolean isEmailCorrect (String email) {return email.matches("^\\w+@icesi\\.edu\\.co$");}
    //===================================================================================

    //Phone Validation
    private boolean isPhoneCorrect (String number) {return number.matches("\\+573\\d{9}$");}

    //===================================================================================

    //Name Validation
    private boolean isFirstnameCorrect (String firstName) {return firstName.matches("^^([a-zA-Z]\\s?){1,120}$");}

    private boolean isLastnameCorrect (String lastName) {return lastName.matches("^([a-zA-Z]\\s?){1,120}$");}

    //===================================================================================
    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
