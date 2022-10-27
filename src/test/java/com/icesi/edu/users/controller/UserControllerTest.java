package com.icesi.edu.users.controller;

import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.security.JWTAuthorizationTokenFilter;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    private JWTAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    @BeforeEach
    private void init() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        jwtAuthorizationTokenFilter = mock(JWTAuthorizationTokenFilter.class);
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testGetNullUser() {

    }

    @Test
    public void testGetUser() {

    }

    @Test
    public void testCreateCorrectUser() {

    }

    @Test
    public void testCreateUserWithWrongName1() {

    }

    @Test
    public void testCreateUserWithWrongName2() {

    }

    @Test
    public void testCreateUserWithWrongName3() {

    }

    @Test
    public void testCreateUserWithWrongName4() {

    }

    @Test
    public void testCreateUserWithWrongEmail1() {

    }

    @Test
    public void testCreateUserWithWrongEmail2() {

    }

    @Test
    public void testCreateUserWithWrongEmail3() {

    }

    @Test
    public void testCreateUserWithWrongNumber1() {

    }

    @Test
    public void testCreateUserWithWrongNumber2() {

    }

    @Test
    public void testCreateUserWithWrongNumber3() {

    }

    @Test
    public void testGetUsers() {

    }

}
