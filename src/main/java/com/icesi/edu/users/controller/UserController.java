package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        validateFieldsPhoneEmail(userDTO);
        validateEmailDomain(userDTO);
        validateEmailSpecialCharacters(userDTO);
        validatePhoneNumberExt(userDTO);
        validateSpacesPhoneNumber(userDTO);
        validatePhoneNumber(userDTO);
        validateNamesLength(userDTO);
        validateNamesSpecialCharacters(userDTO);
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    private void validateFieldsPhoneEmail(UserDTO userDTO) {
        if (userDTO.getEmail() == null && userDTO.getPhoneNumber() == null)
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_01, ErrorConstants.CODE_UD_01.getMessage()));
    }

    private void validateEmailDomain(UserDTO userDTO) {
        if (userDTO.getEmail() != null && !userDTO.getEmail().endsWith(domain))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_02, ErrorConstants.CODE_UD_02.getMessage()));
    }

    private void validateEmailSpecialCharacters(UserDTO userDTO) {
        if (userDTO.getEmail() != null && !userDTO.getEmail().substring(0, userDTO.getEmail().length() - domain.length()).matches("[a-zA-Z0-9]+"))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_03, ErrorConstants.CODE_UD_03.getMessage()));
    }

    private void validatePhoneNumberExt(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().startsWith(phoneExt))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_04, ErrorConstants.CODE_UD_04.getMessage()));
        ;
    }

    private void validateSpacesPhoneNumber(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() != null && userDTO.getPhoneNumber().contains(" "))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_05, ErrorConstants.CODE_UD_05.getMessage()));
    }

    private void validatePhoneNumber(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() != null && !(userDTO.getPhoneNumber().substring(phoneExt.length()).matches("[0-9]+") && userDTO.getPhoneNumber().substring(phoneExt.length()).length() == 10))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_06, ErrorConstants.CODE_UD_06.getMessage()));
    }

    private void validateNamesLength(UserDTO userDTO) {
        if (userDTO.getFirstName().length() > 120 || userDTO.getLastName().length() > 120)
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_07, ErrorConstants.CODE_UD_07.getMessage()));
    }

    private void validateNamesSpecialCharacters(UserDTO userDTO) {
        if (!userDTO.getFirstName().matches("[a-zA-Z]+") || !userDTO.getLastName().matches("[a-zA-Z]+"))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_UD_08, ErrorConstants.CODE_UD_08.getMessage()));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
