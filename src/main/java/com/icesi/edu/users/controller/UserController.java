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
    public UserDTO createUser(UserDTO userDTO) throws Exception {
        boolean isValidUser = validateUser(userDTO);
        if(isValidUser){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            throw new Exception("Invalid data");
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateUser(UserDTO userDTO){
        boolean isValidUser = false;
        if(validateUserEmail(userDTO)){

        }
        return isValidUser;
    }

    private boolean validateUserEmail(UserDTO userDTO) {
        return validateUserEmailDomain(userDTO) && validateUserEmailSpecialChars(userDTO);
    }

    private boolean validateUserEmailSpecialChars(UserDTO userDTO) {
        return false;
    }

    private boolean validateUserEmailDomain(UserDTO userDTO) {
        boolean isValidDomain;
        String domain = userDTO.getEmail().split("@")[1];
        isValidDomain = domain.equals("@icesi.edu.co");
        return isValidDomain;
    }
}
