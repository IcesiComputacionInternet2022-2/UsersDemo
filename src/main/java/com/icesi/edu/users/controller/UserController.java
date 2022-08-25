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
        if (validateEmail(userDTO.getEmail()) && validatePhoneNumber(userDTO.getPhoneNumber())) {
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            return null;
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        /*Pattern pattern = Pattern .compile("((\\+[57]{3,4}|0[57]{4}|00[57]{3})\\-?)?\\d{10,20}");

        Matcher matcher = pattern.matcher(phoneNumber);

        if(matcher.matches()){
            return true;
        }else{
            throw new RuntimeException("Numero Invalido ");
        }*/
    }

    private boolean validateEmail(String email) {
        String[] temp = email.split("@");
        if(temp[1].equals("icesi.edu.co") && (temp[0].matches("^[0-9a-zA-z]*$"))){
            return true;
        }else{
            throw new RuntimeException("Correo Invalido");
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
