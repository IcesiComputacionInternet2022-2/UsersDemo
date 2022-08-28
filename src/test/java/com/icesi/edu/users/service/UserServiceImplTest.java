package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserRepository userRepository;
    private User userTestModel;
    private User anotherUserTestModel;
    private List<User> usersModelList;

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    public void setupScenario1() {
        String email = "Giovanni@icesi.edu.co";
        String phoneNumber = "+573225034089";
        String name = "Giovanni";
        String lastName = "Mosquera";
        userTestModel = new User(UUID.randomUUID(), email, phoneNumber, name, lastName);

        email = "Giovanni@icesi.edu.co";
        phoneNumber = "+573225034089";
        name = "Giovanni";
        lastName = "Mosquera";
        anotherUserTestModel = new User(UUID.randomUUID(), email, phoneNumber, name, lastName);

        usersModelList = new ArrayList<>();
        usersModelList.add(userTestModel);
        usersModelList.add(anotherUserTestModel);
    }

    @Test
    public void createUserGood() {
        setupScenario1();
        when(userRepository.save(userTestModel)).thenReturn(userTestModel);
        User result = userService.createUser(userTestModel);
        assertEquals(result, userTestModel);
        verify(userRepository, times(1)).save(any());
    }

    /*@Test
    public void createUserRepeatedEmail() {
        setupScenario1();
        userService.createUser(userTestModel);
        assertThrows(RuntimeException.class, () -> userService.createUser(anotherUserTestModel));
    }*/

    @Test
    public void getUserTest(){
        setupScenario1();
        when(userRepository.findAllById(any())).thenReturn(any());
        userService.getUser(userTestModel.getId());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void getUsersOk(){
        setupScenario1();
        when(userRepository.findAll()).thenReturn(usersModelList);
        userService.getUsers();
        verify(userRepository, times(1)).findAll();
    }

}
