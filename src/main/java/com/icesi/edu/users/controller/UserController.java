package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
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

        String firstName=userDTO.getFirstName();
        String lastName=userDTO.getLastName();
        String email=userDTO.getEmail();
        verifyEmail(email);
        String phoneNumber=userDTO.getPhoneNumber();
        verifyPhoneNumber(phoneNumber);

        verifyFieldEmailOrPhoneNull(email,phoneNumber);

        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    private void verifyFieldEmailOrPhoneNull(String email, String phoneNumber){
        if (email==null||email.isEmpty()&&phoneNumber==null||phoneNumber.isEmpty()){
            throw new RuntimeException();
        }
    }

    private void verifyPhoneNumber(String phone){
        if (phone==null||phone.isEmpty()){
            return;
        }

        if (!verifyPhoneNumberWithSpaces(phone)){
            String indicativePhone=phone.substring(0,3);
            String phoneNumber=phone.substring(3,phone.length());

            verifyColombianIndicativePhoneNumber(indicativePhone);
            verifyColombianFormatPhoneNumber(phoneNumber);
        }
    }

    private boolean verifyPhoneNumberWithSpaces(String phone){
        boolean phoneNumberContainSpace=false;
        for (int i=0;i<phone.length();i++){
            if (phone.charAt(i)==' '){
                phoneNumberContainSpace=true;
                throw new RuntimeException();
            }
        }

        return phoneNumberContainSpace;
    }


    private void verifyColombianFormatPhoneNumber(String phoneNumber){
        if (phoneNumber!=null && !phoneNumber.matches("^[0-9]*$")){
            throw new RuntimeException();
        }

        if (phoneNumber.length()!=10){
            throw new RuntimeException();
        }
    }
    private void verifyColombianIndicativePhoneNumber(String indicativePhone){
        if(!indicativePhone.trim().equals("+57")){
            throw new RuntimeException();
        }
    }


    private void verifyEmail(String email){
        if (email==null||email.isEmpty()){
            return;
        }

        if (verifyEmailWithOneArroba(email)){
            String emailDomain = email.split("@")[1];
            String emailName = email.split("@")[0];

                verifyEmailDomain(emailDomain);
                verifyEmailWithOutSpecialCharacters(emailName);
        }
    }

    private boolean verifyEmailWithOneArroba(String email){
        int quantyArroba=0;
        if(email==null||email.isEmpty()){
            return false;
        }
        for (int i=0;i<email.length();i++){
            if (email.charAt(i)=='@'){
                quantyArroba++;
            }
        }
        return quantyArroba == 1;
    }

    private void verifyEmailDomain(String emailDomain){
        if (!emailDomain.equals("icesi.edu.co")){
                throw new RuntimeException();
            }
    }

    private void verifyEmailWithOutSpecialCharacters(String emailName){
            if (emailName!=null && !emailName.matches("^[a-zA-Z0-9]*$")){
                throw new RuntimeException();
            }
    }



    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
