package com.icesi.edu.users.service;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testGetUser(){}

    @Test
    public void testCreateUser(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "juca@gmail.com", "+573186215574", "Juca", "Ota");


    }

    @Test
    public void testGetUsers(){}

    @Test
    public void testExistUserWithSamePhoneNumber(){}

    @Test
    public void testExistUserWithSameEmail(){}

}
