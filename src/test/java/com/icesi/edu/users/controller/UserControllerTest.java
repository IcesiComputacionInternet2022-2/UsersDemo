package com.icesi.edu.users.controller;

import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        UserMapper userMapper = new UserMapperImpl();
        userController = new UserController(userService,userMapper);
    }

    @Test
    public void testControllerVerifyPhone(){
        String phone = "+573107115055";
        //assertTrue(userController.ve)
    }
}
