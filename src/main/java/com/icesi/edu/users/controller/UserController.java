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
        verifyEmailDomain(userDTO.getEmail());
        verifyPhoneNumberEspace(userDTO.getPhoneNumber());
        verifyPhoneNumberValidLen(userDTO.getPhoneNumber());
        verifyPhoneNumberOpener(userDTO.getPhoneNumber());

        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private void verifyEmailDomain(String s) {
        if(s==null || !s.matches("[A-Za-z0-9._%+-]+@icesi+\\.+edu+\\.+co"))
            throw new RuntimeException("El correo debe pertenecer al dominio @icesi.edu.co y no contener caracteres especiales");

    }


    private void verifyPhoneNumberOpener(String ps) {
        if (ps!=null && ps.matches("^[+57]+[0-9]"))
            throw new RuntimeException("El numero de telefono debe iniciar con +57");
    }
    private void verifyPhoneNumberEspace(String ps) {
        if (ps==null && ps.matches("\\s"))
            throw new RuntimeException("El numero de telefono no debe contener espacios");
    }
    private void verifyPhoneNumberValidLen(String ps) {
        if(ps==null || ps.length()!=13)
            throw new RuntimeException("El numero de telefono no es del tamaño correcto");
    }

}
