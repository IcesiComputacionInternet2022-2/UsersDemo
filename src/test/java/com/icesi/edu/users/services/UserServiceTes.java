package com.icesi.edu.users.services;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration()
@DataJpaTest
public class UserServiceTes {

    private UserRepository userRepo;
    private UserService userServ;
    private User user1;
    private User user2;
    private List<User> users;

    @BeforeEach
    public void init(){

        userRepo = mock(UserRepository.class);
        userServ = new UserServiceImpl(userRepo);
        users = new ArrayList<>();
    }

    //nicolasp@icesi.edu.co
    @BeforeEach
    public void setup() {

        String email = "diegot145@hotmail.com";
        String phoneNumber = "+573216994239";
        String name = "Diego";
        String lastName = "Torres";

        user1 = new User(UUID.randomUUID(), email, phoneNumber, name, lastName);

        String email2 = "torresprimero2001@gmail.com";
        String phoneNumber2 = "+573162973577";
        String name2 = "Andres";
        String lastName2 = "Primero";

        user2 = new User(UUID.randomUUID(), email2, phoneNumber2, name2, lastName2);

        users.add(user2);
    }

    @Test
    public void addUser() {

        setup();
    }

    @Test
    public void getUsers(){

        setup();
        assertNotNull(users);

        User u = userServ.getUsers().get(0);
        assertEquals(u, user2);
    }

    @Test
    public void getUserTest(){

        setup();

    }


}
