package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User userDTO) {
        emailOrPhoneNumber(userDTO);
        stringNotToLong(userDTO.getFirstName());
        stringNotToLong(userDTO.getLastName());

            return userRepository.save(userDTO);
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private void emailOrPhoneNumber(User userDTO) {
        if(userDTO.getEmail()==null && userDTO.getPhoneNumber()==null){
            throw new RuntimeException("El correo y el numero de telefono no pueden ser vacios por favor llene uno de los dos");
        }
    }
    private void stringNotToLong(String s) {
        if(s ==null || s.length()>120)
            throw new RuntimeException("El Nombre y Apellido no pueden estar en blanco y maximo pueden tener 120 caracteres");
    }
}
