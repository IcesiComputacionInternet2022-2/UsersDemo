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

    private final static String DOMAIN = "@icesi.edu.co";
    private final static String PREFIX = "+57";
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

    public void verifyInput(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();
        if(verifyEmail(email) && verifyPhone(phone)){

        }else{

        }
    }

    private boolean verifyPhone(String phone) {
        return validatePrefix(phone) && validateSpaces(phone) && validNumber(phone);
    }

    private boolean validNumber(String phone) {
        String substring = phone.substring(PREFIX.length()-1);
        return substring.length() == 10;
    }

    private boolean validateSpaces(String phone) {
        return phone.matches("[1-10]+");
    }

    private boolean validatePrefix(String phone) {
        return phone.startsWith(PREFIX);
    }

    private boolean verifyEmail(String email) {
        int idx = email.length() - DOMAIN.length();

        return email != null && verifyDomain(idx, email) && validateMail(idx, email);
    }

    public boolean validateMail(int  endIdx, String email){
        email.substring(0, endIdx);
        return email.matches("[a-zA-Z]+");
    }

    private boolean verifyDomain(int startIdx,String email){
        String substring = email.substring(startIdx);
        return substring.equals(DOMAIN);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
