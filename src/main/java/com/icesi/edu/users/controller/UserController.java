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

    private final int MAX_STRING_LENGTH = 120;
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

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private void checkEmailDomain(final String userEmail){
        if(userEmail != null && !userEmail.matches(".*@icesi\\.edu\\.co"))
            throw new RuntimeException();
    }//End checkEmailDomain

    private void isEmail(final String userEmail){
        if(userEmail != null && !userEmail.split("@")[0].matches(".*A-Za-z0-9"))
            throw new RuntimeException();
    }//End isEmail

    private void checkNumberRegion(final String userPhoneNumber){
        if(userPhoneNumber != null && userPhoneNumber.matches("^\\+57[0-9]{10}$"))
            throw new RuntimeException();
    }//End checkNumberRegion

    private void existNumberOrEmail(final String userPhoneNumber,final String userEmail){
        if(!( (userPhoneNumber != null && !userPhoneNumber.isEmpty())
                || (userEmail != null && !userEmail.isEmpty()) ) )
            throw new RuntimeException();
    }//End existNumberOrEmail

    private void verifyStringLength(final String string){
        if(string.length() > MAX_STRING_LENGTH)
            throw new RuntimeException();
    }//End verifyStringLength


}//End UserController
