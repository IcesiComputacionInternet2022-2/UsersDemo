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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {


    private UserRepository userRepository;
    private UserService userService;

    private UUID id;
    private final String email = "alexandersanchez@icesi.edu.co";
    private final String phone = "+573164713990";
    private final String firstname = "Alexander";
    private final String lastname = "Sanchez";

    @BeforeEach
    public void init () {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    private User scenary () {
        return new User(id,email,phone,firstname,lastname);
    }

    @Test
    public void testGetUser() {
        User user = scenary();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        assertEquals(userService.getUser(id),user);
        verify(userRepository,times(1)).findById(id);
    }

    @Test
    public void testCreateUser() {
        User user = scenary();

        when(userRepository.save(any())).thenReturn(user);
        assertEquals(userService.createUser(user),user);
        verify(userRepository,times(1)).save(any());
    }

    @Test
    public void testGetUsers () {
        List<User> users = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(users);
        assertEquals(userService.getUsers(),users);
    }

    @Test
    public void testEmailRepeated () {
        User user = scenary();
        User userWithEmailRepeated = scenary();
        userWithEmailRepeated.setPhoneNumber("+573014713990");   // Making the phone number different

        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        try {
            userService.createUser(userWithEmailRepeated);
            fail();
        } catch (RuntimeException re) {
            verify(userRepository,times(0)).save(any());
            verify(userRepository,times(2)).findAll();
        }
    }

    @Test
    public void testPhoneRepeated () {
        User user = scenary();
        User userWithPhoneNumberRepeated = scenary();
        userWithPhoneNumberRepeated.setEmail("alexssjr@icesi.edu.co");   // Making the email different

        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        try {
            userService.createUser(userWithPhoneNumberRepeated);
            fail();
        } catch (RuntimeException re) {
            verify(userRepository,times(0)).save(any());
            verify(userRepository,times(2)).findAll();
        }
    }
}
