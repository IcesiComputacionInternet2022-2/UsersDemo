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
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {


    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) throws RuntimeException{
        if(userId == null){
           throw new RuntimeException("UserID can't be empty");
        }else{
            return userMapper.fromUser(userService.getUser(userId));
        }
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws RuntimeException{

        if(validateEmailOrPhone(userDTO) == false){
            throw new RuntimeException("Email or Phone field has to be filled");

        } else if (validateEmailDomain(userDTO.getEmail()) == false) {
            throw new RuntimeException("Email domain not valid");

        } else if (validateEmailUsername(userDTO.getEmail()) == false) {
            throw new RuntimeException("Email username invalid");

        } else if (validatePhoneNumber(userDTO.getPhoneNumber()) == false) {
            throw new RuntimeException("Phone number invalid");

        } else if (validateFirstNameLength(userDTO.getFirstName()) == false) {
            throw new RuntimeException("First name is empty or exceeds limit size (120 characters)");

        } else if (validateLastNameLength(userDTO.getLastName()) == false) {
            throw new RuntimeException("Last name is empty or exceeds limit size (120 characters)");

        } else if (validateFirstNameSpecialCharacters(userDTO.getFirstName()) == false) {
            throw new RuntimeException("First name can't contain special characters nor numbers");

        } else if (validateLastNameSpecialCharacters(userDTO.getLastName()) == false) {
            throw new RuntimeException("Last name can't contain special characters nor numbers");

        }else{
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));

        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateEmailOrPhone(UserDTO userDTO){
        return userDTO.getEmail() != null || userDTO.getPhoneNumber() != null;
    }

    private boolean validateEmailDomain(String email){
        return email.matches("^[a-zA-Z0-9._-]+@icesi\\.edu\\.co$");
    }

    private boolean validateEmailUsername(String email){
        return email.matches("^[a-zA-Z0-9._-]+@icesi\\.edu\\.co$");
    }

    private boolean validatePhoneNumber(String phone){
        return phone.matches("^\\+57[0-9]{10}$");
    }
    private boolean validateFirstNameLength(String firstName){
        return firstName != null && firstName.length() <= 120;
    }

    private boolean validateLastNameLength(String lastName){
        return lastName != null && lastName.length() <= 120;
    }

    private boolean validateFirstNameSpecialCharacters(String firstName){
        return firstName.matches("[a-zA-Z\\s]+");
    }

    private boolean validateLastNameSpecialCharacters(String lastName){
        return lastName.matches("[a-zA-Z\\s]+");
    }








}
