package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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

        List<User> users = getUsers();

        // Avoid adding duplicated data
        for(int i=0; i<users.size();i++){
            if(users.get(i).getEmail().equals(userDTO.getEmail())||users.get(i).getPhoneNumber().equals(userDTO.getPhoneNumber())){
                throw new RuntimeException("Duplicated data");
            }
        }
 

        return userRepository.save(userDTO);
    }

    @Override
    public List<User> getUsers() {
        return   StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
