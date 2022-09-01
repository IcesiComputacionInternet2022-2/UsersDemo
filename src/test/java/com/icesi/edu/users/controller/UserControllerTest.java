package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        userMapper = new UserMapperImpl();
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testController() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+573222050551", "David", "Montano", LocalTime.now());
        when(userService.createUser(any())).thenReturn(userMapper.fromDTO(userDTO));
        userController.createUser(userDTO);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testControllerEmailDomainNotValid() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@ice.edu.co", "+573222050551", "David", "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerEmailContainsSpecialCharacters() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "david-montano1113@icesi.edu.co", "+573222050551", "David", "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerPhoneNumberWrongExtension() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+233222050551", "David", "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerPhoneNumberWithSpaces() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+57322 205 0551", "David", "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerPhoneNumberNotValid() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+5732220505512321", "David", "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerEmailNull() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), null, "+573222050551", "David", "Montano", LocalTime.now());
        when(userService.createUser(any())).thenReturn(userMapper.fromDTO(userDTO));
        userController.createUser(userDTO);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testControllerPhoneAndEmailNull() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), null, null, "David", "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerPhoneNumberNull() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", null, "David", "Montano", LocalTime.now());
        when(userService.createUser(any())).thenReturn(userMapper.fromDTO(userDTO));
        userController.createUser(userDTO);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testControllerFirstNameMoreThan120Characters() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+573222050551",
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901",
                "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerLastNameMoreThan120Characters() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+573222050551", "David",
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901",
                LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerFirstNameSpecialCharacters() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+573222050551", "Dav-id", "Montano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerLastNameSpecialCharacters() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "davidmontano1113@icesi.edu.co", "+573222050551", "David", "Mon-tano", LocalTime.now());
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testControllerEmptyUsers() {
        assertEquals(0, userController.getUsers().size());
    }

    @Test
    public void testControllerSearchNonexistentUser() {
        assertNull(userController.getUser(UUID.randomUUID()));
    }
}