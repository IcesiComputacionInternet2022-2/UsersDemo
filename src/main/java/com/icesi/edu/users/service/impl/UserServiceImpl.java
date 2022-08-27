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
        if(!existRepeatedPhoneNumberOrEmail(userDTO)){
            return userRepository.save(userDTO);
        }
        throw new RuntimeException();
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private boolean existRepeatedPhoneNumberOrEmail(User newUserDTO) {
        boolean repeatedValues = false;
        List<User> users = getUsers();
        for (User user: users) {
            if(user.getPhoneNumber().equals(newUserDTO.getPhoneNumber()) || user.getEmail().equalsIgnoreCase(newUserDTO.getEmail())){
                repeatedValues = true;
                break;
            }
        }

        return repeatedValues;
    }

}
