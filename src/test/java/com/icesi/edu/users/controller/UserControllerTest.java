package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    private UserDTO user;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
        user = new UserDTO(UUID.randomUUID(), "davidparra@icesi.edu.co", "+573148458642", "david", "parra");
    }

    @Test
    public void createUser() {
        try {
            userController.createUser(user);
        }catch (RuntimeException e){
            fail("should not throw an exception.");
        }
    }

    @Test
    public void validateEmailDomain() {
        user.setEmail("davidparra@gmail.com");
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validateSpecialCharacters() {
        user.setEmail("davidp$$rr#@gmail.com");
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validatePhoneCountryCode() {
        user.setPhoneNumber("3148458642");
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validatePhoneSpaces() {
        user.setPhoneNumber("3148 458642");
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validatePhoneLength() {
        user.setPhoneNumber("31484");
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validateCamps() {
        user.setEmail("");
        user.setPhoneNumber("");
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validateNameLength() {
        String name = "";
        for (int i = 0; i<125; i++) name+="a";
        user.setFirstName(name);
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validateName() {
        user.setFirstName("dav15");
        try {
            userController.createUser(user);
            fail("expected exception was not occured.");
        }catch (RuntimeException e){
            //Error detectado correctamente, se lanza RuntimeException
        }
    }
    @Test
    public void validateRepeatedEmails() {

    }
    @Test
    public void validateRepeatedPhones() {

    }
    @Test
    public void validateAddedDate() {

    }
}
