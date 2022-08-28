package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserControllerTest {
    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    private UserDTO userDTO;
    private UUID uuid;

    @BeforeEach
    private void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController=new UserController(userService,userMapper);
        when(userService.createUser(ArgumentMatchers.any())).thenReturn(new User(null,"sebas@u.icesi.edu.co","+573838294922","Sebas","Perez"));
    }

    @Test
    public void getUserTest(){
        when(userService.getUser(ArgumentMatchers.any())).thenReturn(new User(null,"sebas@u.icesi.edu.co","+573838294922","Sebas","Perez"));
        assertEquals("+573838294922",userController.getUser(null).getPhoneNumber());

    }

    @Test
    public void getUsersTest(){
        when(userService.getUsers()).thenReturn(new ArrayList<User>());
        assertTrue(userController.getUsers().isEmpty());
    }

    @Test
    private void createUserTest(){
        UserDTO testUser = new UserDTO(null,"sebas@icesi.edu.co","+573838294922","Sebas","Perez");
        assertEquals(testUser.getPhoneNumber(),userController.createUser(testUser).getPhoneNumber());
    }
    @Test
    public void emailTest(){
        UserDTO userDTO = new UserDTO(null,"perez@ices@i.edu.co","+573838294922","Sebas","Perez");
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");

        assertEquals(thrown.getMessage(),"Invalid data");
    }

    @Test
    public void emailDomainTest(){
        UserDTO userDTO = new UserDTO(null,"sebas@aaaaaaa","+573838294922","Sebas","Perez");
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");
        assertEquals(thrown.getMessage(),"Invalid data");

    }

    @Test
    public void validEmailTest(){
        UserDTO userDTO = new UserDTO(null,"aaa..sfa234*1!@icesi.edu.co","+573838294922","Sebas","Perez");
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");
        assertEquals(thrown.getMessage(),"Invalid data");

    }

    @Test
    public void testVerifyPhoneNumberLength(){
        UserDTO userDTO = new UserDTO(null,"sebas@icesi.edu.co","+5738382922","Sebas","Perez");
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");

        assertEquals(thrown.getMessage(),"Invalid data");
    }

    @Test
    public void countryCodeTest(){
        UserDTO userDTO = new UserDTO(null,"sebas@icesi.edu.co","+5238382922","Sebas","Perez");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");

        assertEquals(thrown.getMessage(),"Invalid data");
    }

    @Test
    public void testVerifyPhoneNumberValidCharacters(){
        UserDTO userDTO = new UserDTO(null,"sebas@icesi.edu.co","+57?8382922","Sebas","Perez");
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");

        assertEquals(thrown.getMessage(),"Invalid data");

    }

    @Test
    public void firstNameLengthTest(){
        UserDTO userDTO = new UserDTO();
        String empty="";
        userDTO.setFirstName(empty);
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");
        assertEquals(thrown.getMessage(),"Invalid data");
    }

    @Test
    public void firstNameLengthTest2(){
        UserDTO userDTO = new UserDTO();
        String length = "";
        int i=0;
        while( i<121){
            length+="N";
            i++;
        }
        userDTO.setFirstName(length);
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");
        assertEquals(thrown.getMessage(),"Invalid data");
    }

    @Test
    public void validFirstnameTest(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("AH,sn-d");
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");

        assertEquals(thrown.getMessage(),"Invalid data");

    }

    @Test
    public void validLastnameTest(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Elsa");
        userDTO.setLastName("AH,sn-d");
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userController.createUser(userDTO);
                }, "Exception");
        assertEquals(thrown.getMessage(),"Invalid data");

    }

}
