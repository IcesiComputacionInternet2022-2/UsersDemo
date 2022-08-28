package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.dto.UserDTO;
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
        if (!validateRepeatedPhone(userDTO.getPhoneNumber()) && !validateRepeatedEmail(userDTO.getEmail())) {
            return userRepository.save(userDTO);
        }else{
            throw new RuntimeException("Repeated Phone Number or Email");
        }
    }

    private boolean validateRepeatedPhone(String phoneNumber) {
        boolean result = false;
        List<User> users = getUsers();

        if (users.size() > 0){
            for(User user : users){
                if(user.getPhoneNumber().equals(phoneNumber)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private boolean validateRepeatedEmail(String email) {
        boolean result = false;
        List<User> users = getUsers();

        if (users.size() > 0) {
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
