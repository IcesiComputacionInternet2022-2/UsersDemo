package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        User user = userRepository.findById(userId).orElse(null);
        if(user != null){
            user.setDate(LocalDate.now().toString());
        }
        return user;
    }

    @Override
    public User createUser(User userDTO) {
        if(validateNonRepeated(userDTO.getEmail(),userDTO.getPhoneNumber()))
            return userRepository.save(userDTO);
        throw new RuntimeException("Repeted email or phoneNumber");
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private boolean validateNonRepeated(String email,String number){
        List<User> users = getUsers();
        for (User x : users){
            if (x.getPhoneNumber().equals(number) || x.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }
}
