package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class UserControllerTest {

    private UserController userController;
    private UserServiceImpl userService;
    private UserMapperImpl userMapper;
    private UserDTO userTest;

    @BeforeEach
    public void init() {
        userService = mock(UserServiceImpl.class);
        userMapper = mock(UserMapperImpl.class);
        userController = new UserController(userService, userMapper);
    }

    // Good user scenario
    private void setupScenario1() {
        String email = "Giovanni@icesi.edu.co";
        String phoneNumber = "+573225034089";
        String name = "Giovanni";
        String lastName = "Mosquera";
        userTest = new UserDTO(UUID.randomUUID(), email, phoneNumber, name, lastName);
    }

    public boolean verifyExceptionsUserCreation() {
        try {
            UserDTO userCreated = userController.createUser(userTest);
        } catch (RuntimeException e) {
            return true;
        }
        return false;
    }

    @Test
    public void createUserTestGood() {
        setupScenario1();
        assertFalse(verifyExceptionsUserCreation());
    }

    @Test
    public void createUserTestBadEmail() {
        setupScenario1();
        userTest.setEmail("Giovanni@dominio.com");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setEmail("Gio");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setEmail("Gioicesi.edu.co");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setEmail("Giovanni@icesi.edu.com");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setEmail("Giovanni%@icesi.edu.com");
        assertTrue(verifyExceptionsUserCreation());
    }

    @Test
    public void createUserTestBadPhone() {
        setupScenario1();
        userTest.setPhoneNumber("+613225034089");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setPhoneNumber("3225034089");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setPhoneNumber("+573225034");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setPhoneNumber("+57322503408%");
        assertTrue(verifyExceptionsUserCreation());
    }

    @Test
    public void createUserTestBadFirstnameLastname() {
        setupScenario1();
        userTest.setFirstName("GiovaanniiGiovaanniiGiovaanniiGiovaanniiGiovaanniiGiovaannii" +
                "GiovaanniiGiovaanniiGiovaanniiGiovaanniiGiovaanniiGiovaanniiGiovaannii");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setLastName("MosqueeraaMosqueeraaMosqueeraaMosqueeraaMosqueeraaMosqueeraa" +
                "MosqueeraaMosqueeraaMosqueeraaMosqueeraaMosqueeraaMosqueeraaMosqueeraa");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setFirstName("Giovanni1%");
        assertTrue(verifyExceptionsUserCreation());
        userTest.setLastName("Mosquera5(");
        assertTrue(verifyExceptionsUserCreation());
    }

    @Test
    public void createUserTestOnlyWithEmail() {
        setupScenario1();
        userTest.setPhoneNumber(null);
        assertFalse(verifyExceptionsUserCreation());
    }

    @Test
    public void createUserTestOnlyWithPhone() {
        setupScenario1();
        userTest.setEmail(null);
        assertFalse(verifyExceptionsUserCreation());
    }

    @Test
    public void createUserTestWithoutPhoneOrEmail() {
        setupScenario1();
        userTest.setEmail(null);
        userTest.setPhoneNumber(null);
        assertTrue(verifyExceptionsUserCreation());
    }

    @Test
    public void getUserTest(){
        userController.getUser(any());
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void getUsersTest(){
        userController.getUsers();
        verify(userService, times(1)).getUsers();
    }

}
