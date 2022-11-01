package com.icesi.edu.users.controller;

import com.google.common.hash.Hashing;
import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserPublicDTO;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< Updated upstream
=======
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
>>>>>>> Stashed changes
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        UserDTO user = userMapper.fromUser(userService.getUser(userId));
        return user;
    }

    @Override
<<<<<<< Updated upstream
    public UserDTO createUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();

        if(!(validateDomain(email) && !hasSpecialCharacters(email) && colombianNumber(phone) && !numberContainsWhiteSpaces(phone) && validSizeNumber(phone)
        && hasAtLeastOneContactWay(email, phone) && namesSizeValidation(firstName, lastName) && !hasSpecialCharactersOnNames(firstName, lastName))){
            throw new RuntimeException("Write valid data and try again. c:");
        }else{
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }
=======
    public UserDTO createUser(@Valid UserDTO userDTO) {
        if(hasAtLeastOneContactWay(userDTO.getEmail(), userDTO.getPhoneNumber())){
            userDTO.setPassword(Hashing.sha256().hashString(userDTO.getPassword(), StandardCharsets.UTF_8).toString());
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }
        throw new UserException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_10, UserErrorCode.CODE_10.getMessage()));
>>>>>>> Stashed changes
    }

    @Override
    public List<UserPublicDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromPublicUser).collect(Collectors.toList());
    }

<<<<<<< Updated upstream
    public boolean validateDomain(String email){
        return email.toUpperCase().matches("^.*(@ICESI\\.EDU\\.CO)$");
    }

    public boolean hasSpecialCharacters(String email){
        String nameEmail = "";

        if(email.split("@").length == 2){
            nameEmail = email.split("@")[0];

        }else{
            return true;
        }
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\-]");
        Matcher matcher = pattern.matcher(nameEmail);
        return matcher.find();
    }

    public boolean colombianNumber(String number){
        return number.substring(0, 3).equalsIgnoreCase("+57");
    }

    public boolean numberContainsWhiteSpaces(String number){
        return number.contains(" ");
    }

    public boolean validSizeNumber(String number){
        if(number != null){
            String actualNumber = number.substring(3);

            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(actualNumber);

            return !matcher.find() && actualNumber.length() == 10;
        }else{
            return true;
        }
    }
=======
>>>>>>> Stashed changes

    public boolean hasAtLeastOneContactWay(String email, String number){
        return ((email != null) && !email.isBlank()) || ((number != null) && !number.isBlank() );
    }
<<<<<<< Updated upstream

    public boolean namesSizeValidation(String firstName, String lastName){
        return (firstName.length() <= 120 && lastName.length() <= 120);
    }

    public boolean hasSpecialCharactersOnNames(String firstName, String lastName){

        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        Matcher matcher1 = pattern.matcher(firstName);
        Matcher matcher2 = pattern.matcher(lastName);

        return (matcher1.find() && matcher2.find());
    }
=======
>>>>>>> Stashed changes
}
