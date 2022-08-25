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

    public final static String DOMAIN = "@icesi.edu.co";

    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(isValidUser(userDTO)){
            if((userDTO.getEmail() != null && isValidEmailDomain(userDTO) && isValidEmail(userDTO)) ||
                    (userDTO.getPhoneNumber() != null && isValidPrefixNumber(userDTO) && isValidPhoneNumber(userDTO))) {
                if(isValidName(userDTO) && isValidNameLength(userDTO)){
                    return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
                }
            }
        }
        throw new RuntimeException();
    }

    private boolean isValidUser(UserDTO userDTO){
        return userDTO != null && userDTO.getFirstName() != null && userDTO.getLastName() != null;
    }

    private boolean isValidEmailDomain(UserDTO userDTO){
        String email = userDTO.getEmail();
        return email.substring(email.length() - DOMAIN.length()).equals(DOMAIN);
    }

    private boolean isValidEmail(UserDTO userDTO){
        String email = userDTO.getEmail();
        email = email.substring(0, email.length() - DOMAIN.length());
        return email.matches("^[a-zA-Z0-9]*$");
    }

    private boolean isValidPrefixNumber(UserDTO userDTO){
        String phoneNumber = userDTO.getPhoneNumber();
        return phoneNumber.substring(0, 3).equals("+57");
    }

    private boolean isValidPhoneNumber(UserDTO userDTO){
        String phoneNumber =  userDTO.getPhoneNumber().substring(3);
        return phoneNumber.matches("^[0-9]*$");
    }

    private boolean isValidNameLength(UserDTO userDTO){
        return userDTO.getFirstName().length() <= 120 && userDTO.getLastName().length() <= 120;
    }

    private boolean isValidName(UserDTO userDTO){
        return userDTO.getFirstName().concat(userDTO.getLastName()).replaceAll(" ", "").matches("^[a-zA-Z]*$");
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
