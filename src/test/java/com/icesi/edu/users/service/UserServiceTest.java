package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    private void init() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    private List<User> setUpUsersList() {
        List<User> users = new ArrayList<>();
        users.add(new User(UUID.randomUUID(), "pepe.perez1@icesi.edu.co", "+573184952830", "Pepe", "Perez"));
        users.add(new User(UUID.randomUUID(), "jose.rodriguez1@icesi.edu.co", "+573184952330", "Jose", "Rodriguez"));
        users.add(new User(UUID.randomUUID(), "martin.suarez2@icesi.edu.co", "+573184959830", "Martin", "Suarez"));
        users.add(new User(UUID.randomUUID(), "juan.sanchez@icesi.edu.co", "+573184959999", "Juan", "Sanchez"));
        users.add(new User(UUID.randomUUID(), "maria.martinez1@icesi.edu.co", "+573184951234", "Maria", "Martinez"));
        return users;
    }

    @Test
    public void testGetANonExistentUser() {
        assertNull(userService.getUser(UUID.randomUUID()));
    }

    @Test
    public void testAddCorrectUser() {
        User toAdd = new User(UUID.randomUUID(), "pepe.perez1@icesi.edu.co", "+573184952830", "Pepe", "Perez");
        when(userRepository.save(any())).thenReturn(toAdd);
        assertEquals(toAdd, userService.createUser(toAdd));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testAddCorrectListOfUsers() {
        List<User> usersList = setUpUsersList();
        for(User u : usersList) {
            when(userRepository.save(any())).thenReturn(u);
            assertEquals(u, userService.createUser(u));
        }
        verify(userRepository, times(5)).save(any());
    }

    private List<User> setUpListWithOneUser() {
        List<User> user = new ArrayList<>();
        user.add(new User(UUID.randomUUID(), "pepe.perez1@icesi.edu.co", "+573184952830", "Pepe", "Perez"));
        return user;
    }

    @Test
    public void testVerifyAddUserWithRepeatedEmail1() {
        when(userRepository.findAll(any())).thenReturn(setUpListWithOneUser());
        User toAdd = new User(UUID.randomUUID(), "pepe.perez1@icesi.edu.co", "+573184952123", "ElPepito", "Sanchez Perez");
        assertThrows(RuntimeException.class, () -> userService.createUser(toAdd));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testVerifyAddUserWithRepeatedPhoneNumber() {
        when(userRepository.findAll(any())).thenReturn(setUpListWithOneUser());
        User toAdd = new User(UUID.randomUUID(), "sancho.panza@icesi.edu.co", "+573184952123", "ElPepito", "Sanchez Perez");
        assertThrows(RuntimeException.class, () -> userService.createUser(toAdd));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testVerifyAddUserWithRepeatedEmailAndNumber() {
        when(userRepository.findAll(any())).thenReturn(setUpListWithOneUser());
        User toAdd = new User(UUID.randomUUID(), "pepe.perez1@icesi.edu.co", "+573184952123", "ElPepito", "Sanchez Perez");
        assertThrows(RuntimeException.class, () -> userService.createUser(toAdd));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testGetSpecificUser() {
        User user = setUpListWithOneUser().get(0);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        assertTrue(user == userService.getUser(user.getId()));
    }

    @Test
    public void testGetListWithOutUsers() {
        assertTrue(userService.getUsers().size() == 0);
    }

    @Test
    public void testGetListWithUsers() {
        List<User> users = setUpUsersList();
        when(userRepository.findAll()).thenReturn(users);
        assertTrue(userService.getUsers().size() == 5);
    }

}
