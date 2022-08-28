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
    public User createUser(User userDTO) throws RuntimeException{

        if(validateRepeatedEmail(userDTO.getEmail()) == false){
            throw new RuntimeException("Email already in use. Please use another one");
        } else if (validateRepeatedPhone(userDTO.getPhoneNumber()) == false) {
            throw new RuntimeException("Phone already in use. Please use another one");
        }else{
            return userRepository.save(userDTO);
        }
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private boolean validateRepeatedEmail(String email){
        return userRepository.findByEmail(email).isEmpty();
    }

    private boolean validateRepeatedPhone(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber).isEmpty();
    }
}
