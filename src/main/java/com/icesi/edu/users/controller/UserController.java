package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserTimeDTO;
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
    public UserTimeDTO getUser(UUID userId) {
        return userMapper.fromUserTime(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if(userDTO.getEmail()!=null && userDTO.getPhoneNumber()!=null){
            if(validateEmail(userDTO.getEmail())&&validatePhoneNumber(userDTO.getPhoneNumber())&&validateName(userDTO.getFirstName())&&validateName(userDTO.getLastName())){
                return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
            }
        }else if(userDTO.getEmail()!=null){
            if(validateEmail(userDTO.getEmail())&&validateName(userDTO.getFirstName())&&validateName(userDTO.getLastName())){
                return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
            }
        }else{
            if(validatePhoneNumber(userDTO.getPhoneNumber())&&validateName(userDTO.getFirstName())&&validateName(userDTO.getLastName())){
                return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
            }
        }

        throw new RuntimeException("Invalid data");

    }


    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateEmail(String email){

        String[] parts = email.split("@");
        return parts[0].matches("^[0-9a-zA-Z]+$") && parts[1].equals("icesi.edu.co");

    }

    private boolean validatePhoneNumber(String phoneNumber){

        String prefix = phoneNumber.substring(0,3);
        String number = phoneNumber.substring(3);

        if(prefix.equals("+57") && number.matches("^[0-9]+$") && number.length()==10){
            return true;
        }else{
            return false;
        }

    }

    private boolean validateName(String name){
        return name.length() <= 120 && name.matches("^[a-zA-Z]+$");
    }

}
