package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;

    private UserService userService;

    private UserMapper userMapper;


    @BeforeEach
    void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService,userMapper);
    }

    @Test
    void TestController(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+571234567890";
        String email = "pepito@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertEquals(userController.createUser(userDTO).getEmail(), userDTO.getEmail());

    }

    @Test
    void testValidateDomain(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+571234567890";
        String email = "pepito@correo.com";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testEmailSpecialSymbol(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+571234567890";
        String email = "Pepito-Perez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testPhoneNumber(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "1234567890";
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testPhoneNumberSpace(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+57 1234567890";
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testPhoneNumberFormat(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+11234567890";
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testPhoneNumberEmailNull(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = null;
        String email = null;

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testEmailNull(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+570123456789";
        String email = null;

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testPhoneNumberNull(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = null;
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testFirstNameLength(){
        String firstName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String lastName = "Perez";
        String phoneNumber = "+571234567890";
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testLastNameLength(){
        String firstName = "Pepito";
        String lastName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String phoneNumber = "+571234567890";
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testFirstNameSpecialSymbol(){
        String firstName = "Pepito_";
        String lastName = "Perez";
        String phoneNumber = "+570123456789";
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }

    @Test
    void testLastNameSpecialSymbol(){
        String firstName = "Pepito";
        String lastName = "Perez//";
        String phoneNumber = "+570123456789";
        String email = "PepitoPerez@icesi.edu.co";

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), email, phoneNumber, firstName, lastName);

        when(userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)))).thenReturn(userDTO);
        assertThrows(RuntimeException.class, () -> {
            userController.createUser(userDTO);
        });
    }
}
