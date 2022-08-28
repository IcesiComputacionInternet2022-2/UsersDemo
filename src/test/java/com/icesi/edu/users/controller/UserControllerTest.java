package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;

    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testControllerGetUser() {
        when(userMapper.fromUser(any())).thenReturn(new UserDTO(UUID.randomUUID(), "johndoe@icesi.edu.co", "+571231230123", "John", "Doe", ""));
        UserDTO userDTOCreate = userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "John", "Doe", ""));
        UserDTO userDTOGet = userController.getUser(userDTOCreate.getId());
        assertNotNull(userDTOGet);
        assertEquals(userDTOCreate, userDTOGet);
    }

    @Test
    public void testControllerGetUserNonExistent() {
        UUID id = UUID.randomUUID();
        assertNull(userController.getUser(id));
        verify(userService, times(1)).getUser(id);
    }

    @Test
    public void testControllerGetUsersEmpty() {
        assertEquals(0, userController.getUsers().size());
        verify(userService, times(1)).getUsers();
    }

    @Test
    public void testControllerGetUsersNonEmpty() {
        assertEquals(0, userController.getUsers().size());
        when(userMapper.fromUser(any())).thenReturn(new UserDTO(UUID.randomUUID(), "johndoe@icesi.edu.co", "+571231230123", "John", "Doe", ""));
        UserDTO userDTOCreate = userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "John", "Doe", ""));
        List<User> users = userService.getUsers();
        users.add(userMapper.fromDTO(userDTOCreate));
        when(userService.getUsers()).thenReturn(users);
        assertEquals(1, userController.getUsers().size());
    }

    @Test
    public void testControllerCreateUser() {
        when(userMapper.fromUser(any())).thenReturn(new UserDTO(UUID.randomUUID(), "johndoe@icesi.edu.co", "+571231230123", "John", "Doe", ""));
        try {
            assertNotNull(userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "John", "Doe", "")));
        } catch (RuntimeException runtimeException) {
            fail("RuntimeException not expected");
        }
    }

    @Test
    public void testControllerCreateUserWithoutRightDomain() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@abc.edu.co", "+571231230123", "John", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserWithInvalidEmail() {
        try {
            userController.createUser(new UserDTO(null, "%$&.@icesi.edu.co", "+571231230123", "John", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserWithDifferentColombianPhoneNumberExtension() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@abc.edu.co", "+501231230123", "John", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserWithInvalidColombianPhoneNumberExtension() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@abc.edu.co", "-571231230123", "John", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserPhoneNumberWithSpaces() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@abc.edu.co", "+57 315 1230123 ", "John", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserWithInvalidPhoneNumberSize() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@abc.edu.co", "+57315123012", "John", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserWithoutEmailAndPhoneNumber() {
        try {
            userController.createUser(new UserDTO(null, "", "", "John", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserWithoutEmailAndWithPhoneNumber() {
        try {
            userController.createUser(new UserDTO(null, "", "+571231230123", "John", "Doe", ""));
            verify(userMapper, times(1)).fromUser(any());
        } catch (RuntimeException runtimeException) {
            fail("RuntimeException not expected");
        }
    }

    @Test
    public void testControllerCreateUserWithoutPhoneNumberAndWithEmail() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "", "John", "Doe", ""));
            verify(userMapper, times(1)).fromUser(any());
        } catch (RuntimeException runtimeException) {
            fail("RuntimeException not expected");
        }
    }

    @Test
    public void testControllerCreateUserFirstNameSizeMoreThan120Characters() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "jdncjlcbwhkwcjelcnelnwcefhuchufbubchfbcfhieuefkycbuoducqjciwelcuifroyuewidbilewlcdvhitoñbyoihnobtirufewdbweuxwbdkedberifr", "Doe", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserLastNameSizeMoreThan120Characters() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "John", "jdncjlcbwhkwcjelcnelnwcefhuchufbubchfbcfhieuefkycbuoducqjciwelcuifroyuewidbilewlcdvhitoñbyoihnobtirufewdbweuxwbdkedberifr", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserFirstNameAndLastNameSizeMoreThan120Characters() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "mnjbhtrehgrejkwneiududfncndeciuelmwdmwuvurioewyvduwrufrovtijbsduwqbsytwdvr4biugtiugfberedjenfhtiungfreuydvwklgriuceirufuw", "jdncjlcbwhkwcjelcnelnwcefhuchufbubchfbcfhieuefkycbuoducqjciwelcuifroyuewidbilewlcdvhitoñbyoihnobtirufewdbweuxwbdkedberifr", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserFirstNameAndLastNameSizeEqualsTo120Characters() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "mnjbhtrehgrejkwneiuddfncndeciuelmwdmwuvurioewyvduwrufrovtijbsdmuwqbsytwdvrbiugtiugfberedjenfhtiungfreuydvwklgriuceirufuw", "jdncjlcbwhkwcjelcnelnwcefhuchufbubchbcfhieuefkycbuoducqjciwelcuifroyuewidbilewlcdvhitonbyoihnobtirufewdbweuxwbdkedberifr", ""));
            verify(userMapper, times(1)).fromUser(any());
        } catch (RuntimeException runtimeException) {
            fail("RuntimeException not expected");
        }
    }

    @Test
    public void testControllerCreateUserEmptyFirstNameAndLastName() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "", "", ""));
            verify(userMapper, times(1)).fromUser(any());
        } catch (RuntimeException runtimeException) {
            fail("RuntimeException not expected");
        }
    }

    @Test
    public void testControllerCreateUserFirstNameAndLastNameWithNumbers() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "John12", "Doe90", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }

    @Test
    public void testControllerCreateUserFirstNameAndLastNameWithSpecialCharacters() {
        try {
            userController.createUser(new UserDTO(null, "johndoe@icesi.edu.co", "+571231230123", "John&%.,", "Doe$!", ""));
            fail("RuntimeException expected");
        } catch (RuntimeException runtimeException) {
            verify(userMapper, times(0)).fromUser(any());
        }
    }
}
