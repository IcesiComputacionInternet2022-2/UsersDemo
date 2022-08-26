package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
        User paramsUser = createUserWithParams(userDTO.getEmail(), userDTO.getPhoneNumber());
        ExampleMatcher matcher = createMatcher();
        List<User> users = fillMatchedUsers(paramsUser, matcher);
        if(users.isEmpty())
            return userRepository.save(userDTO);
        throw new RuntimeException();
    }

    private User createUserWithParams(String email, String phoneNumber) {
        User paramsUser = new User();
        paramsUser.setEmail(email);
        paramsUser.setPhoneNumber(phoneNumber);
        return paramsUser;
    }

    private ExampleMatcher createMatcher() {
        return ExampleMatcher.matchingAny()
                .withIgnorePaths("id")
                .withIgnorePaths("firstName")
                .withIgnorePaths("lastName")
                .withIgnoreNullValues();
    }

    private List<User> fillMatchedUsers(User params, ExampleMatcher matcher) {
        List<User> users = new ArrayList<>();
        userRepository.findAll(Example.of(params, matcher)).forEach(users::add);
        return users;
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

}
