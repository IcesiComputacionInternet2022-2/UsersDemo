package com.icesi.edu.users.controller;

import antlr.Token;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserWithPasswordDTO;
import com.icesi.edu.users.error.constants.ErrorCode;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import com.icesi.edu.users.utils.JWTParser;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;

    private UserController userController;


    @BeforeEach
    public void init(){
        userService = mock(UserServiceImpl.class);
        UserMapper userMapper = new UserMapperImpl();
        userController = new UserController(userService, userMapper);

        when(userService.createUser(ArgumentMatchers.any())).thenReturn(new User(null,"example@u.icesi.edu.co","+573161234567","Pepito","Perez","test123"));

    }

    public UserWithPasswordDTO createTestUserWithPasswordDTO(){
        return new UserWithPasswordDTO(null,"example@icesi.edu.co","+573161234567","Pepito","Perez",null,"test123$%AA");
    }

    @Test
    public void testCreateUser(){
        UserWithPasswordDTO testUser = new UserWithPasswordDTO(null,"example@icesi.edu.co","+573161234567","Pepito","Perez",null,"test123$%AA");
        assertEquals(testUser.getPhoneNumber(),userController.createUser(testUser).getPhoneNumber());
    }

    @Test
    public void testGetUser(){
        UUID userId = UUID.fromString("e485cd3a-255e-4fed-94cd-a03cdd87cf8d");

        User user = new User(userId,"example@u.icesi.edu.co","+573161234567","Pepito","Perez","test123$%AA");

        when(userService.getUser(ArgumentMatchers.any())).thenReturn(user);

        Map<String, String> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());
        TokenDTO tokenDTO = new TokenDTO(JWTParser.createJWT(user.getId().toString(),user.getFirstName(), user.getFirstName(), claims,100000L));

        assertEquals("+573161234567",userController.getUser(userId,tokenDTO).getPhoneNumber());

    }

    @Test
    public void testGetUsers(){
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        assertTrue(userController.getUsers().isEmpty());
    }

    @Test
    public void testVerifyFirstNameLengthMoreThan0(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setFirstName("");

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                    userController.createUser(userDTO)
                , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());


    }

    @Test
    public void testVerifyFirstNameLengthLessThan120(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();

        userDTO.setFirstName("a".repeat(121));

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                    userController.createUser(userDTO)
                , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyFirstNameValidCharacters(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setFirstName("Pe--it&^_");

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                    userController.createUser(userDTO)
                , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyLastNameLengthAtLeast0(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("");

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                                userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyLastNameLengthLessThan120(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("a".repeat(121));

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                                userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyLastNameValidCharacters(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("Pe--it&^_");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                            () -> userController.createUser(userDTO)
                            , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyContactInfoBothNull(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setEmail(null);
        userDTO.setPhoneNumber(null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyContactInfoEmailNull(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setEmail(null);


        userController.createUser(userDTO);

        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyContactInfoPhoneNumberNull(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setPhoneNumber(null);


        userController.createUser(userDTO);

        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyContactInfoBothPresent(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();

        userController.createUser(userDTO);
        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyEmailFormat(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setEmail("exa mple@ices@i.edu.co");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyEmailDomain(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setEmail("example@univalle.edu.co");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyEmailValidCharacters(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setEmail("ex#$%am ple@icesi.edu.co");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyPhoneNumberLength(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setPhoneNumber("+57316123");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyPhoneNumberCountryCode(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setPhoneNumber("+633161234567");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyPhoneNumberValidCharacters(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setPhoneNumber("+57316$%34567");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyPhoneNumberSpaces(){
        UserWithPasswordDTO userDTO = createTestUserWithPasswordDTO();
        userDTO.setPhoneNumber("+573161 34567");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }


}
