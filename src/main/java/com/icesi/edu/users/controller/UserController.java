package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
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
    private final String domain = "@icesi.edu.co";
    private final String phoneExt = "+57";

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (validateFieldsPhoneEmail(userDTO) && validateEmailDomain(userDTO) && validateEmailSpecialCharacters(userDTO)
                && validatePhoneNumberExt(userDTO) && validateSpacesPhoneNumber(userDTO) && validatePhoneNumber(userDTO)
                && validateNamesLength(userDTO) && validateNamesSpecialCharacters(userDTO)) {
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }
        throw new RuntimeException();
    }

    private boolean validateFieldsPhoneEmail(UserDTO userDTO) {
        return userDTO.getEmail() != null || userDTO.getPhoneNumber() != null;
    }

    private boolean validateEmailDomain(UserDTO userDTO) {
        return userDTO.getEmail() == null || userDTO.getEmail().endsWith(domain);
    }

    private boolean validateEmailSpecialCharacters(UserDTO userDTO) {
        return userDTO.getEmail().substring(0, userDTO.getEmail().length() - domain.length()).matches("[a-zA-Z0-9]+");
    }

    private boolean validatePhoneNumberExt(UserDTO userDTO) {
        return userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().startsWith(phoneExt);
    }

    private boolean validateSpacesPhoneNumber(UserDTO userDTO) {
        return userDTO.getPhoneNumber() == null || !userDTO.getPhoneNumber().contains(" ");
    }

    private boolean validatePhoneNumber(UserDTO userDTO) {
        return userDTO.getPhoneNumber() == null || (userDTO.getPhoneNumber().substring(phoneExt.length()).matches("[0-9]+") && userDTO.getPhoneNumber().substring(phoneExt.length()).length() == 10);
    }

    private boolean validateNamesLength(UserDTO userDTO) {
        return userDTO.getFirstName().length() <= 120 && userDTO.getLastName().length() <= 120;
    }

    private boolean validateNamesSpecialCharacters(UserDTO userDTO) {
        return userDTO.getFirstName().matches("[a-zA-Z]+") && userDTO.getLastName().matches("[a-zA-Z]+");
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
