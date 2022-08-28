package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTOConsult;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.time.LocalDate;
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
    public UserDTOConsult getUser(UUID userId) {
        UserDTOConsult userDTOConsult = userMapper.fromUserToUserDTOConsult(userService.getUser(userId));
        userDTOConsult.setLastCall(LocalDate.now());
        return userDTOConsult;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return verifyNulls(userDTO);
    }

    private UserDTO verifyNulls(UserDTO userDTO){
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();

        if(email != null && phone != null) {
            return verifyAllInputs(userDTO, email, phone);
        }else if(email == null && phone != null){
            return verifyInputsWithoutEmail(userDTO, phone);
        }else if(phone == null && email != null){
            return verifyInputsWithoutPhone(userDTO, email);
        }else{
            throw new RuntimeException();
        }
    }

    private UserDTO verifyInputsWithoutEmail(UserDTO userDTO, String phone) {
        String fName = userDTO.getFirstName();
        String lName = userDTO.getLastName();

        if(verifyPhone(phone) && verifyName(fName, lName)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            throw new RuntimeException();
        }
    }

    private UserDTO verifyInputsWithoutPhone(UserDTO userDTO, String email) {
        String fName = userDTO.getFirstName();
        String lName = userDTO.getLastName();

        if(verifyEmail(email) && verifyName(fName, lName)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            throw new RuntimeException();
        }
    }

    private UserDTO verifyAllInputs(UserDTO userDTO, String email, String phone) {
        String fName = userDTO.getFirstName();
        String lName = userDTO.getLastName();

        if(verifyEmail(email) && verifyPhone(phone) && verifyName(fName, lName)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            throw new RuntimeException();
        }
    }

    private boolean verifyName(String firstName, String lastName) {
        System.out.println("verifyNumCharacters: " + verifyNumCharacters(firstName,lastName));
        System.out.println("verifySpecialCharacters: " + verifySpecialCharacters(firstName,lastName));
        return verifyNumCharacters(firstName,lastName) && verifySpecialCharacters(firstName,lastName);
    }

    private boolean verifySpecialCharacters(String firstName, String lastName) {
        return firstName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+");
    }

    private boolean verifyNumCharacters(String firstName, String lastName) {
        return firstName.length() <= 120 && lastName.length() <= 120;
    }

    private boolean verifyPhone(String phone) {
        System.out.println("validatePrefix: " + validatePrefix(phone));
        System.out.println("validateSpaces: " + validateSpaces(phone));
        System.out.println("validNumber: " + validNumber(phone));
        return validatePrefix(phone) && validateSpaces(phone) && validNumber(phone);
    }

    private boolean validNumber(String phone) {
        String substring = phone.substring(PREFIX.length());
        return substring.length() == 10;
    }

    private boolean validateSpaces(String phone) {
        String substring = phone.substring(PREFIX.length());
        return substring.matches("[0-9]+");
    }

    private boolean validatePrefix(String phone) {
        return phone.startsWith(PREFIX);
    }

    private boolean verifyEmail(String email) {
        int idx = email.length() - DOMAIN.length();
        System.out.println("verifyDomain: " + verifyDomain(idx, email));
        System.out.println("validateMail: " + validateMail(idx, email));
        return verifyDomain(idx, email) && validateMail(idx, email);
    }

    public boolean validateMail(int  endIdx, String email){
        String newEmail = email.substring(0, endIdx);
        return newEmail.matches("[a-zA-Z]+");
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

