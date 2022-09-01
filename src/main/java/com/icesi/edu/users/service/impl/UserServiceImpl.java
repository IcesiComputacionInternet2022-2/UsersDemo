package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        validateUniqueUser(user);
        return userRepository.save(user);
    }

    private void validateUniqueUser(User user) {
        if (getUsers().stream().anyMatch(actual -> actual.getEmail().equals(user.getEmail()) || actual.getPhoneNumber().equals(user.getPhoneNumber())))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_09, ErrorConstants.CODE_UD_09.getMessage()));
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
