package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.intellij.lang.annotations.RegExp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static javax.print.attribute.standard.MediaSizeName.A;

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

        if (userDTO.getEmail() != null){

            if (validateEmail(userDTO.getEmail()) == true){

                if (userDTO.getPhoneNumber() != null){

                    if (validatePhoneNumber(userDTO.getPhoneNumber()) == true){

                        if (validateName(userDTO.getFirstName(), userDTO.getLastName()) == true){

                            return userRepository.save(userDTO);
                        }else {

                            return null;
                        }
                    }else {

                        return null;
                    }
                }else {

                    if (validateName(userDTO.getFirstName(), userDTO.getLastName()) == true){

                        return userRepository.save(userDTO);
                    }else{

                        return null;
                    }
                }
            }else{

                return null;
            }
        } else if (userDTO.getPhoneNumber() != null) {

            if (validatePhoneNumber(userDTO.getPhoneNumber())==true){

                if (userDTO.getEmail() != null){

                    if (validateEmail(userDTO.getEmail())== true){

                        if (validateName(userDTO.getFirstName(), userDTO.getLastName()) == true){

                            return userRepository.save(userDTO);
                        }else{

                            return null;
                        }
                    }else {

                        return null;
                    }
                }else{

                    if (validateName(userDTO.getFirstName(), userDTO.getLastName()) == true){

                        return userRepository.save(userDTO);
                    }else {

                        return null;
                    }
                }
            }else {

                return null;
            }
        }else{

            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private boolean validateEmail(String s) {

        String[] email = s.split("@");
        boolean validated = false;

        if (email[1].equals("@icesi.edu.co")) {

            if (email[0].matches("/^[A-Za-z0-9\\s]+$/g")) {

                validated = true;
            }
        }
        return validated;
    }

    private boolean validatePhoneNumber(String s){

        String[] phone = s.split("\\+");
        boolean validated = false;

        if (s.contains("+57")){

            if (phone[1].matches("/^[0-9\\s]+$/g")){

                if (s.length() ==13){

                    validated = true;
                }
            }
        }

        return validated;
    }

    private boolean validateName(String name, String lastname){

        boolean validated = false;
        String fullName = name + " " + lastname;

        if (fullName.length() < 120){

            if (fullName.matches("/^[A-Za-z\\s]+$/g"));{

                validated = true;
            }
        }

        return validated;
    }
}
