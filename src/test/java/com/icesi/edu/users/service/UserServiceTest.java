package com.icesi.edu.users.service;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    private User firstUser;

    @BeforeEach
    void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    void setup(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+570123456789";
        String email = "PepitoPerez@icesi.edu.co";

        User firstUser = new User(UUID.randomUUID(),email,phoneNumber,firstName,lastName);

        when(userService.createUser(firstUser)).thenReturn(firstUser);
        userService.getUsers().add(firstUser);
    }

    @Test
    void testEmailRepeated(){
        String firstName = "Pepito";
        String lastName = "Perez";
        String phoneNumber = "+570123456789";
        String email = "PepitoPerez@icesi.edu.co";

        User firstUser = new User(UUID.randomUUID(),email,phoneNumber,firstName,lastName);

        when(userService.createUser(firstUser)).thenReturn(firstUser);
        userService.createUser(firstUser);

        String firstName2 = "Pepito";
        String lastName2 = "Perez//";
        String phoneNumber2 = "+570000000000";
        String email2 = "PepitoPerez@icesi.edu.co";

        User user = new User(UUID.randomUUID(),email2,phoneNumber2,firstName2,lastName2);

        when(userService.createUser(user)).thenReturn(user);
        assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });
    }


}
