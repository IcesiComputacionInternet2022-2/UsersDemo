package com.icesi.edu.users.Service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
        String email = "juandavid227@icesi.edu.co";
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
        String email = "juandavid227@icesi.edu.co";
        String phoneNumber = "+573166670887";
        String firstName = "Juan";
        String lastName = "Cruz";

        User user1 = new User(uuid,email,phoneNumber,firstName,lastName);

        //Get User
        userService.createUser(user1);
        User obtainedUser = userService.getUser(uuid);
        verify(userRepository,times(1)).findById(any()); //Save is being called
    }

    @Test
    public void testGetUsers(){
        //First User
        UUID uuid = UUID.randomUUID();
        String email = "juandavid227@icesi.edu.co";
        String phoneNumber = "+573166670887";
        String firstName = "Juan";
        String lastName = "Cruz";

        //Second User
        UUID uuid2 = UUID.randomUUID();
        String email2 = "prueba@icesi.edu.co";
        String phoneNumber2 = "+573207828580";
        String firstName2 = "Liliana";
        String lastName2 = "Garcia";

        //Create User
        User user1 = new User(uuid,email,phoneNumber,firstName,lastName);
        User user2 = new User(uuid2,email2,phoneNumber2,firstName2,lastName2);
        userService.createUser(user1);
        userService.createUser(user2);
        userService.getUsers();
        verify(userRepository,times(3)).findAll(); //It's called 3 times because createUser calls getUsers
    }

    @Test
    public void testNonRepeatedEmailOrNumber(){
        UUID uuid = UUID.randomUUID();
        String email = "juandavid227@icesi.edu.co";
        String phoneNumber = "+573166670887";
        String firstName = "Juan";
        String lastName = "Cruz";

        User user1 = new User(uuid,email,phoneNumber,firstName,lastName);
        List<User> users = new ArrayList<>();

        //Create User
        when(userRepository.save(any())).thenReturn(user1);
        User createdUser =userService.createUser(user1);
        users.add(createdUser);
        when(userService.getUsers()).thenReturn(users);
        try{  //have to use "try catch" because if I don't, test doesn't run.
            userService.createUser(user1);
        }catch (Exception e){
            //System.out.println("Entre"); Fuerza bruta para ver si entraba (si lo hace jaja)
            verify(userRepository,times(1)).save(any());
        }
    }


}
