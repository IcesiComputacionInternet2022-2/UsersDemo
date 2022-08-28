package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository = null;

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User userDTO) {
    	if(verifyUser(userDTO)) {
    		return userRepository.save(userDTO);
    	}
    	else {
    		return null;
    	}
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
    
    
    private boolean verifyUser(User user){
    	User temp;
        List<User> users = getUsers();
        if(!users.isEmpty()) {
        	for(int i=0;i<=users.size();i++) {
            	temp = users.get(i);
            	if(temp.getPhoneNumber().equals(user.getPhoneNumber()) || temp.getEmail().equals(user.getEmail())) {
            		return false;
            	}
            }
        }
        return true;
    }

}
