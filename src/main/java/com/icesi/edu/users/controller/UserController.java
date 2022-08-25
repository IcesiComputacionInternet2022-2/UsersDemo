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
        String[] email=userDTO.getEmail().split("@");
        if(validateDomain(userDTO.getEmail())
                &&validateSize(userDTO.getEmail())
                &&validateEmail(email[0])
               ){
        }
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    public boolean validateDomain(String email) {
        if (email.contains("@icesi.edu.co")) {
            return true;
        } else {
            return false;
        }
    }
    public boolean validateSize(String email){
        if(email.split("@").length==2){
            return true;
        }


        return false;
    }

    public boolean validateEmail(String email) {
        boolean check=false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) >= 47 && email.charAt(i) < 58
                    || email.charAt(i) >= 64 && email.charAt(i) <= 91
                    || email.charAt(i) >= 97 && email.charAt(i) < 122
                    || email.charAt(i)==46) {
                    check=true;
            }
        }
        return check;
    }
    public boolean validateCountryCode(String number){
        String code = number.charAt(0)+number.charAt(1)+number.charAt(2)+"";
        if(code=="+57"){
            return true;
        }
        return false;
    }

    public boolean validatePhoneNumber(String number){
        boolean check=false;
        for(int i=3;i<number.length();i++){
            if((number.charAt(i)>=48 && number.charAt(i)<=57)&&number.length()==13){
                check=true;
            }
            else{
                return check =false;
            }
        }
        return check;
    }

    public boolean validateNotEmpty(UserDTO user){
        if(user.getEmail()!=null||user.getPhoneNumber()!=null){
            return true;
        }
        return false;
    }

    public boolean validateName(String firstName, String lastName){
        boolean check=false;
        if(firstName.length()<=120&&lastName.length()<=120){
            for(int i=0;i<firstName.length();i++){
                if(firstName.charAt(i)<=90||firstName.charAt(i)>=65
                ||firstName.charAt(i)>=97||firstName.charAt(i)<=122){
                    check=true;
                }
                else{
                    return check=false;
                }
            }
            for(int i=0;i<lastName.length();i++){
                if(lastName.charAt(i)<=90||lastName.charAt(i)>=65
                        ||lastName.charAt(i)>=97||lastName.charAt(i)<=122){
                    check=true;
                }
                else{
                    return check=false;
                }
            }
        }
    return check;
    }
}