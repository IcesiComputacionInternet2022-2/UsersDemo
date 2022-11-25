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
        isValidUser(userDTO);
        isValidEmailDomain(userDTO);
        isValidEmail(userDTO);
        isValidPrefixPhoneNumber(userDTO);
        isValidPhoneNumberLength(userDTO);
        isValidPhoneNumber(userDTO);
        isValidNameLengthSum(userDTO);
        isValidName(userDTO);
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    private void isValidUser(UserDTO userDTO){
        if(userDTO == null || (userDTO.getEmail() == null && userDTO.getPhoneNumber() == null) || userDTO.getFirstName() == null || userDTO.getLastName() == null)
            throw new RuntimeException();
    }

    private void isValidEmailDomain(UserDTO userDTO){
        if(userDTO.getEmail() != null && !userDTO.getEmail().endsWith(DOMAIN))
            throw new RuntimeException();
    }

    private void isValidEmail(UserDTO userDTO){
        if(userDTO.getEmail() != null && !userDTO.getEmail().substring(0, userDTO.getEmail().length() - DOMAIN.length()).matches("^[a-zA-Z0-9]*$"))
            throw new RuntimeException();
    }

    private void isValidPrefixPhoneNumber(UserDTO userDTO){
        if(userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().startsWith("+57"))
            throw new RuntimeException();
    }

    private void isValidPhoneNumberLength(UserDTO userDTO){
        if(userDTO.getPhoneNumber() != null && !(userDTO.getPhoneNumber().substring(3).length() == 10))
            throw new RuntimeException();
    }

    private void isValidPhoneNumber(UserDTO userDTO){
        if(userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().substring(3).matches("^[0-9]*$"))
            throw new RuntimeException();
    }

    private void isValidNameLengthSum(UserDTO userDTO){
        if(userDTO.getFirstName().length() + userDTO.getLastName().length() > 120)
            throw new RuntimeException();
    }

    private void isValidName(UserDTO userDTO){
        if(!userDTO.getFirstName().concat(userDTO.getLastName()).replaceAll(" ", "").matches("^[a-zA-Z]*$"))
            throw new RuntimeException();
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

}
