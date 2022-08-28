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
        String name=userDTO.getFirstName();
        String lastName=userDTO.getLastName();
        String email= userDTO.getEmail();
        String phoneNumber= userDTO.getPhoneNumber();

        if(verifyUserFirstName(name)
                && verifyUserLastName(lastName)
                && verifyUserEmailAndPhoneNumber(email,phoneNumber)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }
        else{
            throw new RuntimeException("Throw new RuntimeException");
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean verifyUserFirstName(String userFirstName){
        if(userFirstName==null||userFirstName.isEmpty())return false;

        return userFirstName.length() <= 120  && userFirstName.matches("^[a-zA-Z]*$");
    }

    private boolean verifyUserLastName(String lastName){
        if (lastName==null||lastName.isEmpty()) return false;

        return lastName.length() <= 120 && lastName.matches("^[a-zA-Z]*$");
    }

    private boolean verifyUserEmailAndPhoneNumber(String email, String phoneNumber){
        boolean emailAndPhoneIsCorrect = false;
        if(phoneNumber != null){
            emailAndPhoneIsCorrect = verifyUserPhoneNumber(phoneNumber);
            if(!emailAndPhoneIsCorrect) return false;
        }

        if (email != null){
            emailAndPhoneIsCorrect = verifyUserEmail(email);
            if(!emailAndPhoneIsCorrect) return false;
        }
        return emailAndPhoneIsCorrect;
    }
    private boolean verifyUserEmail(String email){

        String[] divideEmail = email.split("@");

        //Verify if there is one @, later get the second part with de domain, and verify that the first part not have special characters
        return divideEmail.length == 2 && divideEmail[0].matches("^[a-zA-Z0-9]*$") && divideEmail[1].equals("icesi.edu.co") ;
    }

    private boolean verifyUserPhoneNumber(String phoneNumber){

        //Verify if have the correct format of a colombian number phone +57XXXXXXXXXX
        return  phoneNumber.substring(0,3).equals("+57") && phoneNumber.substring(3,13).matches("^[0-9]*$") && phoneNumber.length() == 13;
    }
}