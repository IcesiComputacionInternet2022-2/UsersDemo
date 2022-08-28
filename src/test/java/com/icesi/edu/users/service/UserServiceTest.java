package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private User testNewUserToAdd;

    private UserRepository userRepository;

    private UserService userService;


    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }


    private void createUsers(){
        List<User> usersTest = new ArrayList<>();
        User user1= new User(null,"gustavo@icesi.edu.co","+573108724713","Gustavo","Adolfo");
        User user2= new User(null,"emailRepeated@icesi.edu.co","+573225286612","Wanda","Villacorte");

        usersTest.add(user1);
        usersTest.add(user2);

        when(userRepository.findAll()).thenReturn(usersTest);
    }

    @Test
    public void testCreateUser(){


        testNewUserToAdd = new User(null,"gustavo@icesi.edu.co","+573201234567","Gustavo","Villada");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testNewUserToAdd);

        User user = userService.createUser(testNewUserToAdd);
        assertEquals(testNewUserToAdd,user);
    }

    @Test
    public void testGetUser(){

        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        User user = userService.getUser(null);
        assertNull(user);
    }


    @Test
    public void testGetUsers(){

        when(userRepository.findAll()).thenReturn(new ArrayList<User>());
        List<User> users = userService.getUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    public void testVerifyRepeatedEmail(){
        createUsers();

        testNewUserToAdd = new User(null,"emailRepeated@icesi.edu.co","+573201234567","Gustavo","Villada");

        RuntimeException Exception = assertThrows(RuntimeException.class, () -> {userService.createUser(testNewUserToAdd);});
        assertEquals(Exception.getMessage(),"Throw new RuntimeException");

    }


    @Test
    public void testVerifyEmailNotRepeated(){
        createUsers();

        testNewUserToAdd = new User(null,"emailNotRepeated@icesi.edu.co","+573201234567","Gustavo","Villada");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testNewUserToAdd);

        User user = userService.createUser(testNewUserToAdd);
        assertEquals(testNewUserToAdd, user);

    }

    @Test
    public void testVerifyPhoneNumberRepeated(){
        createUsers();

        testNewUserToAdd = new User(null,"numberRepeated@icesi.edu.co","+573108724713","Gustavo","Villada");

        RuntimeException Exception = assertThrows(RuntimeException.class, () -> {userService.createUser(testNewUserToAdd);});
        assertEquals(Exception.getMessage(),"Throw new RuntimeException");

    }

    @Test
    public void testVerifyPhoneNumberNotRepeated(){
        createUsers();

        testNewUserToAdd = new User(null,"numberNotRepeated@icesi.edu.co","+571234567890","Gustavo","Villada");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testNewUserToAdd);

        User user = userService.createUser(testNewUserToAdd);
        assertEquals(testNewUserToAdd,user);
    }












}