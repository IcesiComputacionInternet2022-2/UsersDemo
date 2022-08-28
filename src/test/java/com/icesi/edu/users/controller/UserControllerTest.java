package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
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

    private UserService userService;

    private UserController userController;

    private UserMapper userMapper;


    @BeforeEach
    public void init(){
        userService = mock(UserServiceImpl.class);
        userMapper = new UserMapperImpl();
        userController = new UserController(userService,userMapper);
    }

    @Test
    public void testCreateUser(){
        when(userService.createUser(ArgumentMatchers.any())).thenReturn(new User(null,"gustavo@icesi.edu.co","+573108724713","Gustavo","Villada"));
        UserDTO testUser = new UserDTO(null,"wanda@icesi.edu.co","+573108724713","Wanda","Villacorte",null);
        assertEquals(testUser.getPhoneNumber(),userController.createUser(testUser).getPhoneNumber());
    }


    @Test
    public void testGetUsers(){
        ArrayList<User> list= new ArrayList<User>();
        list.add(new User(null,"gustavo@icesi.edu.co","+573108724713","Gustavo","Villada"));
        when(userService.getUsers()).thenReturn(list);
        assertTrue(userController.getUsers().get(0).getFirstName().equals("Gustavo"));
    }



    @Test
    public void testVerifyIfFirstNameLengthIsMoreThan120(){

        UserDTO userDTO = new UserDTO(null,"gustavo@icesi.edu.co","+573108724713","Gustavo","Villada",null);
        String firstName = "";

        for (int i=0;i<125;i++){
            firstName+="X";
        }

        userDTO.setFirstName(firstName);
        RuntimeException exception =assertThrows(RuntimeException.class, () -> {userController.createUser(userDTO);} );
        assertEquals(exception.getMessage(),"Throw new RuntimeException");


        UserDTO userDTOCorrect = new UserDTO(null,"gustavo@icesi.edu.co","+573087224713","Gustavo","Villada",null);
        userController.createUser(userDTOCorrect);
    }

    @Test
    public void testVerifyFirstNameHaveSpecialCharacters(){
        UserDTO userDTO = new UserDTO(null,"gustavo@icesi.edu.co","+573108724713","Gustavo","Villada",null);
        String firstName="Gu#st!vO";
        userDTO.setFirstName(firstName);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> { userController.createUser(userDTO);});
        assertEquals(exception.getMessage(),"Throw new RuntimeException");


        UserDTO userDTOCorrect = new UserDTO(null,"gustavo@icesi.edu.co","+573087224713","Gustavo","Villada",null);
        userController.createUser(userDTOCorrect);

    }

    @Test
    public void testVerifyIfLastNameLengthIsMoreThan120(){
        UserDTO userDTO = new UserDTO(null,"gustavo@icesi.edu.co","+573108724713","Gustavo","Villada",null);
        String lastName = "";

        for (int i=0;i<130;i++){
            lastName+="X";
        }

        userDTO.setLastName(lastName);
        RuntimeException exception =assertThrows(RuntimeException.class, () -> {userController.createUser(userDTO);} );
        assertEquals(exception.getMessage(),"Throw new RuntimeException");

        UserDTO userDTOCorrect = new UserDTO(null,"gustavo@icesi.edu.co","+573087224713","Gustavo","Villada",null);
        userController.createUser(userDTOCorrect);
    }

    @Test
    public void testVerifyLastNameHaveSpecialCharacters(){
        UserDTO userDTO = new UserDTO();
        String lastName="Gu#st!vO";
        userDTO.setLastName(lastName);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> { userController.createUser(userDTO);});
        assertEquals(exception.getMessage(),"Throw new RuntimeException");

        UserDTO userDTOCorrect = new UserDTO(null,"gustavo@icesi.edu.co","+573087224713","Gustavo","Villada",null);
        userController.createUser(userDTOCorrect);

    }
    
    @Test
    public void testVerifyEmailDomain(){
        UserDTO userDTO = new UserDTO(null,"gustavo@javeriana.edu.co","+573108724713","Gustavo","Villada",null);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {userController.createUser(userDTO);});
        assertEquals(thrown.getMessage(),"Throw new RuntimeException");


        UserDTO userDTOCorrect = new UserDTO(null,"gustavo@icesi.edu.co","+573087224713","Gustavo","Villada",null);
        userController.createUser(userDTOCorrect);
    }

    @Test
    public void testVerifyPhoneNumber(){
        UserDTO userDTO = new UserDTO(null,"gustavo@javeriana.edu.co","+52308724713","Gustavo","Villada",null);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {userController.createUser(userDTO);});
        assertEquals(thrown.getMessage(),"Throw new RuntimeException");



        UserDTO userDTOCorrect = new UserDTO(null,"gustavo@icesi.edu.co","+573087224713","Gustavo","Villada",null);
        userController.createUser(userDTOCorrect);
    }


    @Test
    public void testVerifyEmailFormat(){
        UserDTO userDTO = new UserDTO(null,"gustav!o@icesi@.edu.co","+52308724713","Gustavo","Villada",null);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {userController.createUser(userDTO);});
        assertEquals(thrown.getMessage(),"Throw new RuntimeException");


        UserDTO userDTOCorrect = new UserDTO(null,"gustavo@icesi.edu.co","+573087224713","Gustavo","Villada",null);
        userController.createUser(userDTOCorrect);
    }



}