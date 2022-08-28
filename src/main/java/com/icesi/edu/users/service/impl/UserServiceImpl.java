package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
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
        if(findEmail(userDTO)) {
            throw new RuntimeException("This email already exists");
        }
        return userRepository.save(userDTO);
    }

    private boolean findEmail(User user) {
        Iterable<User> users = userRepository.findAll();
        AtomicBoolean found = new AtomicBoolean(false);
        users.forEach((p) -> {
            if(p.getEmail().equals(user.getEmail())) {
                found.set(true);
            }
        });
        return found.get();
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
