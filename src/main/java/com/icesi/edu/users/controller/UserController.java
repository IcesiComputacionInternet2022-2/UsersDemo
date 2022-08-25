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
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    //Email Validation
    public boolean belongToDomain (String string) {return string.endsWith(domain);}

    private String getUsernameDomain (String email) {return email.substring(0,email.indexOf(domain));}

    public boolean isValidEmail (String emailUsername) {
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailUsername);

        return matcher.find();
    }

    //Phone Validation
    public boolean hasColombianPrefix (String number) {return number.startsWith("+57");}

    public boolean hasWhitespaces (String number) {return number.contains(" ");}

    public boolean isValidNumber (String number) {return number.matches("(\\+573)\\d{10}");}

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
