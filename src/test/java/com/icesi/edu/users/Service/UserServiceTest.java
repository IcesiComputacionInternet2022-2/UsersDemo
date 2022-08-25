package com.icesi.edu.users.Service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    private void init(){
        userRepository = mock(UserRepository.class);
        userService= new UserServiceImpl(userRepository);
    }

    @Test
    public void testCreateUser(){
        UUID uuid = UUID.randomUUID();
        String email = "juandavid227@hotmail.com";
        String phoneNumber = "+573166670887";
        String firstName = "Juan";
        String lastName = "Cruz";

        User user1 = new User(uuid,email,phoneNumber,firstName,lastName);

        //Create User
        when(userRepository.save(any())).thenReturn(new User());
        User createdUser = userService.createUser(user1);
        assertNotNull(createdUser); //User is not null

        verify(userRepository,times(1)).save(any()); //Save is being called
    }

    @Test
    public void testGetUser(){
        UUID uuid = UUID.randomUUID();
        String email = "juandavid227@hotmail.com";
        String phoneNumber = "+573166670887";
        String firstName = "Juan";
        String lastName = "Cruz";

        User user1 = new User(uuid,email,phoneNumber,firstName,lastName);

        //Get User
        User obtainedUser = userService.getUser(uuid);
        verify(userRepository,times(1)).findById(any()); //Save is being called
    }

    @Test
    public void testGetUsers(){
        //First User
        UUID uuid = UUID.randomUUID();
        String email = "juandavid227@hotmail.com";
        String phoneNumber = "+573166670887";
        String firstName = "Juan";
        String lastName = "Cruz";

        //Second User
        UUID uuid2 = UUID.randomUUID();
        String email2 = "prueba@hotmail.com";
        String phoneNumber2 = "+573207828580";
        String firstName2 = "Liliana";
        String lastName2 = "Garcia";

        //Create User
        User user1 = new User(uuid,email,phoneNumber,firstName,lastName);
        User user2 = new User(uuid2,email2,phoneNumber2,firstName2,lastName2);

        userService.getUsers();
        verify(userRepository,times(1)).findAll(any());


    }

}
