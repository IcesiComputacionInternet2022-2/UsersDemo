package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        validEmail(userDTO);
        validPhoneNumber(userDTO);
        return userRepository.save(userDTO);
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private void validEmail(User userDTO){
        if(userDTO.getEmail() != null) {
            Optional<User> userFound = userRepository.findByEmail(userDTO.getEmail());
            if(userFound.isPresent())
                throw new RuntimeException();
        }
    }

    private void validPhoneNumber(User userDTO){
        if(userDTO.getPhoneNumber() != null) {
            Optional<User> userFound = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
            if(userFound.isPresent())
                throw new RuntimeException();
        }
    }

}
