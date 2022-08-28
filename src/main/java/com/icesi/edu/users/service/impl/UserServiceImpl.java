package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId){
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User userDTO){
        if(!isEmailOrPhoneNumberDuplicated(userDTO))
            return userRepository.save(userDTO);
        else
            throw new RuntimeException();
    }

    private boolean isEmailOrPhoneNumberDuplicated(User userDTO){
        boolean isEmailDuplicated = getUsers().stream().anyMatch(
                user -> user.getEmail().equalsIgnoreCase(userDTO.getEmail()));
        boolean isPhoneNumberDuplicated = getUsers().stream().anyMatch(
                user -> user.getPhoneNumber().equals(userDTO.getPhoneNumber()));
        return isEmailDuplicated || isPhoneNumberDuplicated;
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
