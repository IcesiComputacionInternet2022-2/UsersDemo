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

    private UserRepository userRepository;

    private UserService userService;

    private List<User> users;

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
        users = new ArrayList<>();
    }

    public void stage1() {
        User user = new User(null,"johndoe@icesi.edu.co", "+579876543219", "John", "Doe");
        userService.createUser(user);
        users.add(user);
    }

    @Test
    public void testGetUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id,"johndoe@icesi.edu.co", "+579876543219", "John", "Doe");
        when(userRepository.save(any())).thenReturn(new User(id,"johndoe@icesi.edu.co", "+579876543219", "John", "Doe"));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User(id, "johndoe@icesi.edu.co", "+579876543219", "John", "Doe")));
        User userCreate = userService.createUser(user);
        User userGet = userService.getUser(id);
        assertNotNull(userGet);
        assertEquals(userCreate, userGet);
    }

    @Test
    public void testGetUserNonExistent() {
        UUID id = UUID.randomUUID();
        assertNull(userService.getUser(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testGetUsersEmpty() {
        assertEquals(0, userService.getUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUsersNonEmpty() {
        assertEquals(0, userService.getUsers().size());
        when(userRepository.save(any())).thenReturn(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+579876543219", "John", "Doe"));
        User userCreate = userService.createUser(new User(null, "johndoe@icesi.edu.co", "+571231230123", "John", "Doe"));
        List<User> users = new ArrayList<>();
        users.add(userCreate);
        when(userService.getUsers()).thenReturn(users);
        assertEquals(1, userService.getUsers().size());
    }

    @Test
    public void testCreateUser() {
        try {
            userService.createUser(new User(null,"johndoe@icesi.edu.co", "+571231230123", "John", "Doe"));
            verify(userRepository, times(1)).save(any());
        } catch(RuntimeException runtimeException) {
            fail("RuntimeException not expected");
        }
    }

    @Test
    public void testCreateUserWithRepeatedEmail() {
        try {
            stage1();
            when(userService.getUsers()).thenReturn(users);
            userService.createUser(new User(null,"johndoe@icesi.edu.co", "+571231230123", "John", "Doe"));
            fail("RuntimeException expected");
        } catch(RuntimeException runtimeException) {
            verify(userRepository, times(1)).save(any());
            assertEquals(1, userService.getUsers().size());
        }
    }

    @Test
    public void testCreateUserWithRepeatedPhoneNumber() {
        try {
            stage1();
            when(userService.getUsers()).thenReturn(users);
            userService.createUser(new User(null,"dohndoe123@icesi.edu.co", "+579876543219", "John", "Doe"));
            fail("RuntimeException expected");
        } catch(RuntimeException runtimeException) {
            verify(userRepository, times(1)).save(any());
            assertEquals(1, userService.getUsers().size());
        }
    }

    @Test
    public void testCreateUserWithRepeatedEmailAndPhoneNumber() {
        try {
            stage1();
            when(userService.getUsers()).thenReturn(users);
            userService.createUser(new User(null,"johndoe@icesi.edu.co", "+579876543219", "John", "Doe"));
            fail("RuntimeException expected");
        } catch(RuntimeException runtimeException) {
            assertEquals(1, userService.getUsers().size());
            verify(userRepository, times(1)).save(any());
        }
    }
}
