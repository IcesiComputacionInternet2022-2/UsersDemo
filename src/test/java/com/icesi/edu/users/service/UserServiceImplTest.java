package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;

@SpringBootTest
public class UserServiceImplTest {

    void stage1(){
        User u1 = new User(UUID.randomUUID(),"Jhon@icesi.edu.co","+573152324657","Jhon","Paredes",new Date());
    }

    @Autowired
    private UserService userService;

    @Test
    public void UserAddsCorrectly(){
            userService.createUser(new User(UUID.randomUUID(),"Jhon@icesi.edu.co","+573152324657","Jhon","Paredes",new Date()));
            List<User> users = (List<User>) userService.getUsers();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void UserPhoneNumberIsUnique(){
        userService.createUser(new User(UUID.randomUUID(), "carlosmontana@icesi.edu.co", "+573152324657", "carlos", "montana", new Date()));
        Assertions.assertThrows(RuntimeException.class,() ->userService.createUser(new User(UUID.randomUUID(), "Jhon@icesi.edu.co", "+573152324657", "Jhon", "Paredes", new Date())));
    }

    @Test
    public void UserPhoneNumberAndEmailCantbeNull(){
        Assertions.assertThrows(RuntimeException.class,() ->userService.createUser(new User(UUID.randomUUID(), null, null, "Jhon", "Paredes", new Date())));
    }
}
