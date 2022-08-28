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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        if(validateEmailPhoneNull(userDTO) && validateEmail(userDTO.getEmail()) && validatePhoneNumber(userDTO.getPhoneNumber()) && validateFirstName(userDTO.getFirstName()) && ValidateLastName(userDTO.getLastName())){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            return null;
        }
    }

    private boolean validateEmailPhoneNull(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() != null || userDTO.getEmail() != null){
            return true;
        }else{
            throw new RuntimeException("There must be a Phone Number or Email");
        }
    }

    private boolean validateFirstName(String firstName) {
        if(firstName != null && firstName.length() > 0 && firstName.matches("^[0-9a-zA-z]*$") && firstName.length() <= 120){
            return true;
        }else{
            throw new RuntimeException("Invalid First Name");
        }
    }

    private boolean ValidateLastName(String lastName) {
        if(lastName != null && lastName.length() > 0 && lastName.matches("^[0-9a-zA-z]*$") && lastName.length() <= 120){
            return true;
        }else{
            throw new RuntimeException("Invalid Last Name");
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if(phoneNumber.length() == 13 && phoneNumber.startsWith("+57") && phoneNumber.substring(3).length() == 10 && phoneNumber.substring(3).matches("^[0-9]*$")){
            return true;
        }else{
            throw new RuntimeException("Invalid Phone Number");
        }
    }

    private boolean validateEmail(String email) {
        if (email.split("@")[1].equals("icesi.edu.co") && (email.split("@")[0].matches("^[0-9a-zA-z]*$"))){
            return true;
        }else{
            throw new RuntimeException("Invalid Email");
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
