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
        System.out.println(validateUserEmail(userDTO) +" "+ validateUserPhoneNumber(userDTO) +" "+
                validateEmailOrPhoneNumberNotNull(userDTO) +" "+ validateFirstName(userDTO) +" "+
                validateLastName(userDTO));
        if(validateEmailOrPhoneNumberNotNull(userDTO) && validateFirstName(userDTO) &&
                validateLastName(userDTO)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateUserEmail(UserDTO userDTO){
        String email = userDTO.getEmail();
        return email != null && email.toUpperCase().endsWith("@icesi.edu.co") &&
                email.substring(0, email.length()-13).matches("[a-zA-Z0-9]+");
    }

    private boolean validateUserPhoneNumber(UserDTO userDTO){
        String phoneNumber = userDTO.getPhoneNumber();
        return phoneNumber != null &&
                phoneNumber.startsWith("+57") && phoneNumber.substring(1).matches("[0-9]+");
    }

    private boolean validateEmailOrPhoneNumberNotNull(UserDTO userDTO){
        return userDTO.getEmail()!=null || userDTO.getPhoneNumber()!=null;
    }

    private boolean validateFirstName(UserDTO userDTO){
        String firstName = userDTO.getFirstName();
        return firstName != null && firstName.length() <= 120 && firstName.matches("[a-zA-Z]+");
    }

    private boolean validateLastName(UserDTO userDTO){
        String lastName = userDTO.getFirstName();
        return lastName != null || lastName.length() <= 120 && lastName.matches("[a-zA-Z]+");
    }
}
