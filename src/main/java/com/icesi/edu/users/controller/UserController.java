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

    public final UserService userService = null;
    public final UserMapper userMapper = null;

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
    	if (verifyUser(userDTO)) return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    	return null;
    }

    @Override
    public List<UserDTO> getUsers() {
    	return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

	public boolean verifyUser(UserDTO user) {
		boolean answer = verifyEmail(user.getEmail()) && verifyPhone(user.getPhoneNumber()) && verifyName(user.getFirstName()) && verifyName(user.getLastName());
		return answer;
	}
	
	private boolean verifyEmail(String email){
        String emailEntered = "\\w+@icesi.edu.co$";
        if(email.matches(emailEntered)) {
        	return true;
        }else {
        	return false;
        }
    }

    private boolean verifyPhone(String phone){
        String phoneEntered = "^(\\+57)[0-9]{10}";
        if(phone.matches(phoneEntered)) {
        	return true;
        }else {
        	return false;
        }
    }

    private boolean verifyName(String name){
        String nameEntered = "[aA-zZ]";
        if(name.matches(nameEntered)) {
        	return true;
        }else {
        	return false;
        }
    }
}
