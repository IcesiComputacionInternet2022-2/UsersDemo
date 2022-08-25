package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {


    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        userDTO.setId(UUID.randomUUID());
        switch (verifyOnlyCase(email, phone)) {
            case 0:
                System.out.println("Case " + verifyOnlyCase(email, phone));
                if (verifyEmail(email) && verifyNames(firstName, lastName)) {

                } else { throw new RuntimeException(); }
                break;
            case 1:
                if (verifyPhone(phone) && verifyNames(firstName, lastName)) {

                } else { throw new RuntimeException(); }
                break;
            case 2:
                if (verifyEmail(email) && verifyPhone(phone) && verifyNames(firstName, lastName)) {

                } else { throw new RuntimeException(); }
                break;
        }

        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    private int verifyOnlyCase(String email, String phone) {
        if(email != null && phone == null) {
            return 0;
        } else if (phone != null && email == null) {
            return 1;
        }
        return 2;
    }

    private boolean verifyEmail(String email) {
        return email.matches("^[A-Za-z0-9._-]+@icesi.edu\\.co$");
    }

    private boolean verifyPhone(String phone) {
        return phone.length() == 13 && phone.substring(0,3).equals("+57") && phone.substring(3,13).matches("[0-9]+");
    }

    private boolean verifyNames(String firstName, String lastName) {
        return firstName != null && lastName != null && firstName.length() <= 120 && lastName.length() <= 120 && firstName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+");
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
