package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

        verifyEmailRepeat(userDTO.getEmail())  ;
        verifyPhoneNumberRepeat(userDTO.getPhoneNumber());
            return userRepository.save(userDTO);

    }

    @Override
    public List<User> getUsers() {
        List<User> userList=StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());

        return userList;
    }

    public void verifyEmailRepeat(String email){
        for (User i:getUsers()) {
            if (i.getEmail().equals(email)){
                throw new RuntimeException("Throw new RuntimeException");
            }
        }
    }

    public void verifyPhoneNumberRepeat(String phoneNumber){
        for (User i:getUsers()) {
            if (i.getPhoneNumber().equals(phoneNumber)){
                throw new RuntimeException("Throw new RuntimeException");
            }
        }
    }


}
