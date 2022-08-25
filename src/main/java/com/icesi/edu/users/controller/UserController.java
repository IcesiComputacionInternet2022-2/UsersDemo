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
            throw new RuntimeException();
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateUser(UserDTO userDTO){
        return validateUserNonNulls(userDTO) && validateUserEmail(userDTO) && validateUserPhoneNumber(userDTO) && validateUserNames(userDTO);
    }

    private boolean validateUserNonNulls(UserDTO userDTO){
        return validateUserEmailNotNull(userDTO.getEmail()) && validateUserPhoneNotNull(userDTO.getPhoneNumber());
    }

    private boolean validateUserPhoneNotNull(String phoneNumber) {
        return phoneNumber != null;
    }

    private boolean validateUserEmailNotNull(String email) {
        return email != null;
    }

    private boolean validateUserNames(UserDTO userDTO) {
        return validateUserNamesSize(userDTO) && validateUserNamesContent(userDTO);
    }

    private boolean validateUserNamesContent(UserDTO userDTO) {
        return userDTO.getFirstName().matches("[a-zA-Z]+") && userDTO.getLastName().matches("[a-zA-Z]+");
    }

    private boolean validateUserNamesSize(UserDTO userDTO) {
        return userDTO.getFirstName().length() == 120 && userDTO.getLastName().length() == 120;
    }

    private boolean validateUserPhoneNumber(UserDTO userDTO) {
        if(validateUserPhoneNotNull(userDTO.getPhoneNumber())){
            return validateUserPhoneNumberExtension(userDTO) && validateUserPhoneNumberContent(userDTO);
        }
        return false;
    }

    private boolean validateUserPhoneNumberContent(UserDTO userDTO) {
        String phoneNumber = userDTO.getPhoneNumber();
        return phoneNumber.length() == "+57XXXXXXXXXX".length() && phoneNumber.matches("[0-9]*");
    }

    private boolean validateUserPhoneNumberExtension(UserDTO userDTO) {
        return userDTO.getPhoneNumber().startsWith("+57%");
    }

    private boolean validateUserEmail(UserDTO userDTO){
        if(validateUserEmailNotNull(userDTO.getEmail())){
            String[] email = userDTO.getEmail().split("@");
            if(email.length == 2){
                return  validateUserEmailDomain(email[1]) && validateUserEmailSpecialChars(email[0]);
            }
        }

        return false;
    }

    private boolean validateUserEmailSpecialChars(String user) {
        return user.matches("[a-zA-Z0-9]+");
    }

    private boolean validateUserEmailDomain(String domain) {
        return domain.equals("@icesi.edu.co");
    }

    
}
