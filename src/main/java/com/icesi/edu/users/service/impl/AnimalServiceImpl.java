package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.Animal;
import com.icesi.edu.users.repository.AnimalRepository;
import com.icesi.edu.users.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {

    public final AnimalRepository userRepository;

    @Override
    public Animal getUser(UUID userId) {

        return userRepository.findById(userId).orElse(null);
    }



    @Override
    public Animal createUser(Animal userDTO) {

        verifyEmailRepeat(userDTO.getEmail())  ;
        verifyPhoneNumberRepeat(userDTO.getPhoneNumber());
            return userRepository.save(userDTO);

    }

    @Override
    public List<Animal> getUsers() {
        List<Animal> userList=StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());

        return userList;
    }

    public void verifyEmailRepeat(String email){
        for (Animal i:getUsers()) {
            if (i.getEmail().equals(email)){
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - Email repeated in the database"));
            }
        }
    }

    public void verifyPhoneNumberRepeat(String phoneNumber){
        for (Animal i:getUsers()) {
            if (i.getPhoneNumber().equals(phoneNumber)){
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - Phone repeated in the database"));

            }
        }
    }


}
