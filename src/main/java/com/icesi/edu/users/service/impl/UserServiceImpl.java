package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.Permission;
import com.icesi.edu.users.model.Role;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.PermissionRepository;
import com.icesi.edu.users.repository.RoleRepository;
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

    public final RoleRepository roleRepository;

    public final PermissionRepository permissionRepository;

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User userDTO, UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow();
        userDTO.setRole(role);
        List<Permission> permissions = StreamSupport.stream(permissionRepository.findAll().spliterator(),false).collect(Collectors.toList());
        return userRepository.save(userDTO);
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
