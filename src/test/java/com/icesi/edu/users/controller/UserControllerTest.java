package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTORequest;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserService userService;
    private UserMapper userMapper;
    private UserController userController;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testGetUser(){
        UUID id = UUID.randomUUID();
        String requestDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        UserDTORequest expectedDTO = new UserDTORequest(id, "juca@icesi.edu.co", "+573186215574", "Juca", "Ota", requestDate);

        when(userMapper.fromUserToDTORequest(any())).thenReturn(expectedDTO);
        when(userService.getUser(any())).thenReturn(new User(id, "juca@icesi.edu.co", "+573186215574", "Juca", "Ota"));

        assertEquals(userController.getUser(id), expectedDTO);
        verify(userMapper, times(1)).fromUserToDTORequest(any());
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void testGetNotSavedUser(){
        UUID id = UUID.randomUUID();
        String requestDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        UserDTORequest expectedDTO = new UserDTORequest();
        expectedDTO.setRequestDate(requestDate);

        when(userMapper.fromUserToDTORequest(any())).thenReturn(expectedDTO);
        when(userService.getUser(any())).thenReturn(null);

        assertEquals(userController.getUser(id), expectedDTO);
        verify(userMapper, times(1)).fromUserToDTORequest(any());
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void testCreateUser(){}

    @Test
    public void testGetUsers(){}

}
