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
    public User createUser(User user) {

        if(verifyRepeatedEmail(user)){
            return userRepository.save(user);
        }
        else{
            throw new RuntimeException("Repeated email or phone number");
        }
    }

    private boolean verifyRepeatedEmail(User user){

        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());

        for(User registeredUser: users){
            if(user.getEmail().toLowerCase().equals(registeredUser.getEmail().toLowerCase())
            || user.getPhoneNumber().equals(registeredUser.getPhoneNumber())){
                return false;
            }
        }

        return true;
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
