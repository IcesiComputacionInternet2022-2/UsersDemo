package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserMapper userMapper;
    private UserService userService;

    private final UUID id = UUID.randomUUID();
    private final String email = "alexandersanchez@icesi.edu.co";
    private final String phone = "+573164713990";
    private final String firstname = "Alexander";
    private final String lastname = "Sanchez";

    @BeforeEach
    public void init () {
        userService = mock(UserService.class);
        userMapper = new UserMapperImpl();
        userController = new UserController(userService,userMapper);
    }

    private UserDTO exampleUserDTO () {
        return new UserDTO(id,email,phone,firstname,lastname,LocalTime.now());
    }

    private User exampleUser () {return new User(id,email,phone,firstname,lastname);}
    @Test
    public void testGetUserMethod() {
        // UserDTO userDTO = exampleUserDTO();
        User user = exampleUser();
        when(userService.getUser(any())).thenReturn(user);
        UserDTO returnedUser = userController.getUser(id);
        verify(userService,times(1)).getUser(any());
    }

    @Test
    public void testCreateUserMethod () {
        UserDTO userDTO = exampleUserDTO();
        User user = exampleUser();
        when(userService.createUser(any())).thenReturn(user);
        userController.createUser(userDTO);
        verify(userService,times(1)).createUser(any());
    }

    @Test
    public void testGetUsers () {
        List<User> users = new ArrayList<>();
        List<UserDTO> usersDTO = new ArrayList<>();

        when(userService.getUsers()).thenReturn(users);

        assertEquals(userController.getUsers(),usersDTO);
        verify(userService,times(1)).getUsers();
    }

    @Test
    public void testWrongDomain () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setEmail("alexssjr@gmail.com");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testWrongCharacters () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setEmail("alexandersanchez.jr@icesi.edu.co");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testWrongPrefix () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setPhoneNumber("+583176162821");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testPhoneWithSpaces () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setPhoneNumber("+57 3164713990");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testWrongPhoneFormat () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setPhoneNumber("+5747947139900");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testWrongFirstname () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setFirstName("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis nato");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testWrongLastname () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setLastName("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis nato");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testSpecialCharsOnFirstname () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setFirstName("Alexander 2");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService,times(0)).createUser(any());
        }
    }

    @Test
    public void testSpecialCharsOnLastname () {
        UserDTO userDTO = exampleUserDTO();
        userDTO.setLastName("Sánchez");

        try {
            userController.createUser(userDTO);
            fail();
        } catch (RuntimeException re) {
            verify(userService, times(0)).createUser(any());
        }
    }
}
