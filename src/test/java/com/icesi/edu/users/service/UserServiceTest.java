package com.icesi.edu.users.service;

import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.Animal;
import com.icesi.edu.users.repository.AnimalRepository;
import com.icesi.edu.users.service.impl.AnimalServiceImpl;
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

    private Animal testNewUserToAdd;

    private AnimalRepository userRepository;

    private AnimalService userService;


    @BeforeEach
    public void init(){
        userRepository = mock(AnimalRepository.class);
        userService = new AnimalServiceImpl(userRepository);
    }


    private void createUsers(){
        List<Animal> usersTest = new ArrayList<>();
        Animal user1= new Animal(null,"gustavo@icesi.edu.co","+573108724713","Gustavo","Adolfo");
        Animal user2= new Animal(null,"emailRepeated@icesi.edu.co","+573225286612","Wanda","Villacorte");

        usersTest.add(user1);
        usersTest.add(user2);

        when(userRepository.findAll()).thenReturn(usersTest);
    }

    @Test
    public void testCreateUser(){


        testNewUserToAdd = new Animal(null,"gustavo@icesi.edu.co","+573201234567","Gustavo","Villada");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testNewUserToAdd);

        Animal user = userService.createUser(testNewUserToAdd);
        assertEquals(testNewUserToAdd,user);
    }

    @Test
    public void testGetUser(){

        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Animal user = userService.getUser(null);
        assertNull(user);
    }


    @Test
    public void testGetUsers(){

        when(userRepository.findAll()).thenReturn(new ArrayList<Animal>());
        List<Animal> users = userService.getUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    public void testVerifyRepeatedEmail(){
        createUsers();

        testNewUserToAdd = new Animal(null,"emailRepeated@icesi.edu.co","+573201234567","Gustavo","Villada");

        RuntimeException Exception = assertThrows(RuntimeException.class, () -> {userService.createUser(testNewUserToAdd);});
        assertEquals(Exception.getMessage(),"Throw new RuntimeException");

    }


    @Test
    public void testVerifyEmailNotRepeated(){
        createUsers();

        testNewUserToAdd = new Animal(null,"emailNotRepeated@icesi.edu.co","+573201234567","Gustavo","Villada");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testNewUserToAdd);

        Animal user = userService.createUser(testNewUserToAdd);
        assertEquals(testNewUserToAdd, user);

    }

    @Test
    public void testVerifyPhoneNumberRepeated(){
        createUsers();

        testNewUserToAdd = new Animal(null,"numberRepeated@icesi.edu.co","+573108724713","Gustavo","Villada");

        UserDemoException userException = assertThrows(UserDemoException.class, () -> {userService.createUser(testNewUserToAdd);});
        assertEquals(userException.getError().getMessage(),"Throw UserDemoException - Phone repeated in the database");

    }

    @Test
    public void testVerifyPhoneNumberNotRepeated(){
        createUsers();

        testNewUserToAdd = new Animal(null,"numberNotRepeated@icesi.edu.co","+571234567890","Gustavo","Villada");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testNewUserToAdd);

        Animal user = userService.createUser(testNewUserToAdd);
        assertEquals(testNewUserToAdd,user);
    }












}