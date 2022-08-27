package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserRepository userRepository;

    private UserService userService;

    private UserController userController;

    private UserMapper userMapper;


    @BeforeEach
    public void init(){
        userService = mock(UserServiceImpl.class);
        userMapper = new UserMapperImpl();
        userController = new UserController(userService,userMapper);

        when(userService.createUser(ArgumentMatchers.any())).thenReturn(new User(null,"example@u.icesi.edu.co","+573161234567","Pepito","Perez"));

    }

    @Test
    public void testCreateUser(){
        UserDTO testUser = new UserDTO(null,"example@icesi.edu.co","+573161234567","Pepito","Perez",null);
        assertEquals(testUser.getPhoneNumber(),userController.createUser(testUser).getPhoneNumber());
    }

    @Test
    public void testGetUser(){
        when(userService.getUser(ArgumentMatchers.any())).thenReturn(new User(null,"example@u.icesi.edu.co","+573161234567","Pepito","Perez"));

        assertEquals("+573161234567",userController.getUser(null).getPhoneNumber());

    }

    @Test
    public void testGetUsers(){
        when(userService.getUsers()).thenReturn(new ArrayList<User>());

        assertTrue(userController.getUsers().isEmpty());
    }

    @Test
    public void testVerifyFirstNameLengthMoreThan0(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");
    }

    @Test
    public void testVerifyFirstNameLengthLessThan120(){
        UserDTO userDTO = new UserDTO();

        String firstName = "";

        for(int i=0; i < 121; i++){
            firstName += "a";
        }

        userDTO.setFirstName(firstName);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");

    }

    @Test
    public void testVerifyFirstNameValidCharacters(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Pe--it&^_");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");

    }

    @Test
    public void testVerifyLastNameLengthAtLeast0(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");
    }

    @Test
    public void testVerifyLastNameLengthLessThan120(){
        UserDTO userDTO = new UserDTO();

        String lastName = "";

        for(int i=0; i < 121; i++){
            lastName += "a";
        }

        userDTO.setFirstName("Juan");
        userDTO.setLastName(lastName);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");

    }

    @Test
    public void testVerifyLastNameValidCharacters(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("Pe--it&^_");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");

    }

    @Test
    public void testVerifyContactInfoBothNull(){
        UserDTO userDTO = new UserDTO(null,null,null,"Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");
    }

    @Test
    public void testVerifyContactInfoEmailNull(){
        UserDTO userDTO = new UserDTO(null,null,"+573161234567","Juan","Perez",null);

        userController.createUser(userDTO);

        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyContactInfoPhoneNumberNull(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co",null,"Juan","Perez",null);

        userController.createUser(userDTO);

        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyContactInfoBothPresent(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+573161234567","Juan","Perez",null);

        userController.createUser(userDTO);
        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyEmailFormat(){
        UserDTO userDTO = new UserDTO(null,"exa mple@ices@i.edu.co","+573161234567","Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");
    }

    @Test
    public void testVerifyEmailDomain(){
        UserDTO userDTO = new UserDTO(null,"example@univalle.edu.co","+573161234567","Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");

    }

    @Test
    public void testVerifyEmailValidCharacters(){
        UserDTO userDTO = new UserDTO(null,"ex#$%am ple@icesi.edu.co","+573161234567","Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");

    }

    @Test
    public void testVerifyPhoneNumberLength(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+57316123","Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");
    }

    @Test
    public void testVerifyPhoneNumberCountryCode(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+633161234567","Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");
    }

    @Test
    public void testVerifyPhoneNumberValidCharacters(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+57316$%34567","Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");

    }

    @Test
    public void testVerifyPhoneNumberSpaces(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+573161 34567","Juan","Perez",null);

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Incorrect attributes format and/or values");
    }


}
