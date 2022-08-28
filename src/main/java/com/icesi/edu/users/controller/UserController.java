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

        if (userDTO.getEmail() != null){

            if (validateEmail(userDTO.getEmail())){

                if (userDTO.getPhoneNumber() != null){

                    if (validatePhoneNumber(userDTO.getPhoneNumber())){

                        if (validateName(userDTO.getFirstName(), userDTO.getLastName())){

                            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
                        }else {

                            return null;
                        }
                    }else {

                        return null;
                    }
                }else {

                    if (validateName(userDTO.getFirstName(), userDTO.getLastName())){

                        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
                    }else{

                        return null;
                    }
                }
            }else{

                return null;
            }
        } else if (userDTO.getPhoneNumber() != null) {

            if (validatePhoneNumber(userDTO.getPhoneNumber())){

                if (userDTO.getEmail() != null){

                    if (validateEmail(userDTO.getEmail())){

                        if (validateName(userDTO.getFirstName(), userDTO.getLastName())){

                            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
                        }else{

                            return null;
                        }
                    }else {

                        return null;
                    }
                }else{

                    if (validateName(userDTO.getFirstName(), userDTO.getLastName())){

                        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
                    }else {

                        return null;
                    }
                }
            }else {

                return null;
            }
        }else {

            return null;
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }


    private boolean validateEmail(String s) {

        String[] email = s.split("@");
        boolean validated = false;

        if (email[1].equals("@icesi.edu.co")) {

            if (email[0].matches("/^[A-Za-z0-9\\s]+$/g")) {

                validated = true;
            }
        }
        return validated;
    }

    private boolean validatePhoneNumber(String s){

        String[] phone = s.split("\\+");
        boolean validated = false;

        if (s.contains("+57")){

            if (phone[1].matches("/^[0-9\\s]+$/g")){

                if (s.length() ==13){

                    validated = true;
                }
            }
        }

        return validated;
    }

    private boolean validateName(String name, String lastname){

        boolean validated = false;
        String fullName = name + " " + lastname;

        if (fullName.length() < 120){

            if (fullName.matches("/^[A-Za-z\\s]+$/g"));{

                validated = true;
            }
        }

        return validated;
    }

    public boolean validateRepeatEmail(String s){

        boolean validated = false;

        for (int i = 0; i <  userService.getUsers().size() ;i++){

            if (s.equals(userService.getUsers().get(i).getEmail())){


                validated = true;
            }
        }

        return validated;
    }

    public boolean validateRepeatPNumber(String s){

        boolean validated = false;

        for (int i = 0; i <  userService.getUsers().size() ;i++){

            if (s.equals(userService.getUsers().get(i).getPhoneNumber())){


                validated = true;
            }
        }

        return validated;
    }
}
