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

    @Test
    public void testServiceGetUserNonexistent() {
        assertNull(userService.getUser(UUID.randomUUID()));
    }

    @Test
    public void testServiceGetUser() {
        User user = User.builder().id(UUID.randomUUID()).email("davidmontano1113@icesi.edu.co").phoneNumber("+573222050551").firstName("David").lastName("Montano").build();
        when(userRepository.save(any())).thenReturn(user);
        userService.createUser(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUser(user.getId()));
    }

    @Test
    public void testServiceCreateUser() {
        User user = User.builder().id(UUID.randomUUID()).email("davidmontano1113@icesi.edu.co").phoneNumber("+573222050551").firstName("David").lastName("Montano").build();
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user, userService.createUser(user));
        verify(userRepository, times(1)).save(any());
    }

    private List<User> listWithOneUser() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(UUID.randomUUID()).email("davidmontano1113@icesi.edu.co").phoneNumber("+573222050551").firstName("David").lastName("Montano").build());
        return users;
    }

    @Test
    public void testServiceEmailAlreadyUsed() {
        List<User> users = listWithOneUser();
        when(userService.getUsers()).thenReturn(users);
        User secondUser = User.builder().id(UUID.randomUUID()).email("davidmontano1113@icesi.edu.co").phoneNumber("+571234567890").firstName("David").lastName("Montano").build();
        assertThrows(RuntimeException.class, () -> userService.createUser(secondUser));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testServicePhoneNumberAlreadyUsed() {
        List<User> users = listWithOneUser();
        when(userService.getUsers()).thenReturn(users);
        User secondUser = User.builder().id(UUID.randomUUID()).email("david1113@icesi.edu.co").phoneNumber("+573222050551").firstName("David").lastName("Montano").build();
        assertThrows(RuntimeException.class, () -> userService.createUser(secondUser));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testServiceEmailAndPhoneNumberAlreadyUsed() {
        List<User> users = listWithOneUser();
        when(userService.getUsers()).thenReturn(users);
        User secondUser = User.builder().id(UUID.randomUUID()).email("davidmontano1113@icesi.edu.co").phoneNumber("+573222050551").firstName("David").lastName("Montano").build();
        assertThrows(RuntimeException.class, () -> userService.createUser(secondUser));
        verify(userRepository, times(0)).save(any());
    }
}
