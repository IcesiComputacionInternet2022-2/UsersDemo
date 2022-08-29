package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private User testUser1;
    private User testUser2;
    private User testUser3;
    private UUID id1;
    private UUID id2;
    private UUID id3;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        id1 = UUID.randomUUID();
        testUser1 = new User(id1, "juan@icesi.edu.co", "+573334445555", "Juan", "Calderon");

        id2 = UUID.randomUUID();
        testUser2 = new User(id2, "jose@icesi.edu.co", "+571112223333", "Jose", "Calderon");

        id3 = UUID.randomUUID();
        testUser3 = new User(id3, "felipe@icesi.edu.co", "+570009998888", "Felipe", "Calderon");

        when(userRepository.save(testUser1)).thenReturn(testUser1);
        when(userRepository.save(testUser2)).thenReturn(testUser2);

    }

    private boolean throwsException(User argUser){
        try{
            userService.createUser(argUser);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    @Test
    public void testCreateValidUser(){
        assertFalse(throwsException(testUser1));
        verify(userRepository, atLeastOnce()).save(any());
    }

    @Test
    public void testCreateRepeatedEmail(){
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(testUser1));

        userService.createUser(testUser2);
        testUser2.setEmail("jose@icesi.edu.co");
        assertTrue(throwsException(testUser3));
        verify(userRepository, never()).save(any());
    }



}
