package com.icesi.edu.users.controller;

import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserMapper userMapper;
    private UserService userService;
    private UserDTO testUser;
    private User userResponse;

    @BeforeEach
    public void init(){
        userService =  mock(UserService.class);
        userMapper = mock(UserMapper.class);

        userController = new UserController(userService, userMapper);

        UUID id = UUID.randomUUID();
        testUser = new UserDTO(id, "juan@icesi.edu.co", "+573012345678", "Juan Jose", "Calderon");
        userResponse = new User(id, "juan@icesi.edu.co", "+573012345678", "Juan Jose", "Calderon");

        when(userService.createUser(any())).thenReturn(userResponse);
    }

    private boolean throwsException(UserDTO argUser){
        try{
            UserDTO userDto = userController.createUser(argUser);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    @Test
    public void testCreateValidUser(){
        assertFalse(throwsException(testUser));
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testCreateUserWithoutEmailNorPhone(){
        testUser.setEmail(null);
        testUser.setPhoneNumber(null);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmailDomainWrong(){
        testUser.setEmail("juan@gmail.edu.co");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmailUsernameWrong(){
        testUser.setEmail("!:{}*&+@icesi.edu.co");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithCountryIndicatorWrong1(){
        testUser.setPhoneNumber("+123012345678");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithCountryIndicatorWrong2(){
        testUser.setPhoneNumber("573012345678");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidAmountOfNumberSizeShorter(){
        testUser.setPhoneNumber("+573012345");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidAmountOfNumberSizeLonger(){
        testUser.setPhoneNumber("+5730123456789");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmptyFirstName(){
        testUser.setFirstName(null);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmptyLastName(){
        testUser.setLastName(null);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithLongerFirstName(){
        String longName = "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "a";
        testUser.setFirstName(longName);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithLongerLastName(){
        String longName = "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "a";
        testUser.setLastName(longName);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithFirstNameWithSpecialCharacters(){
        testUser.setFirstName("Juan#34");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithLastNameWithSpecialCharacters(){
        testUser.setLastName("C4lder0n^_^");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }




}
