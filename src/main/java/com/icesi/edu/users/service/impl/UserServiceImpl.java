package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTOConsult;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User userDTO) {
        return searchRepeat(userDTO);
    }

    private User searchRepeat(User userDTO){
        List<User> list = (List<User>) userRepository.findAll();
        boolean repeat = false;


        for(User i:list){

            if((i.getEmail() != null && i.getEmail().equalsIgnoreCase(userDTO.getEmail())) ||
                    (i.getPhoneNumber() != null && i.getPhoneNumber().equalsIgnoreCase(userDTO.getPhoneNumber()))){
                repeat = true;
                break;
            }else{
                repeat =false;
            }
        }
        if(!repeat){
            return userRepository.save(userDTO);
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
