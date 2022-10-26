package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.AnimalAPI;
import com.icesi.edu.users.dto.AnimalDTO;
import com.icesi.edu.users.mapper.AnimalMapper;
import com.icesi.edu.users.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AnimalController implements AnimalAPI {

    private static Integer ERROR_NAME =101;
    private static Integer ERROR_LAST_NAME =102;
    private static Integer ERROR_PHONE =201;
    private static Integer ERROR_EMAIL =202;


    public final AnimalService userService;
    public final AnimalMapper userMapper;

    @Override
    public AnimalDTO getAnimal(UUID animalId) {

        return userMapper.fromAnimal(userService.getUser(animalId));
    }

    @Override
    public AnimalDTO createAnimal(AnimalDTO userDTO) {

            return userMapper.fromAnimal(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<AnimalDTO> getAnimals() {
        return userService.getUsers().stream().map(userMapper::fromAnimal).collect(Collectors.toList());
    }
    /*

    private boolean verifyUserFirstName(String userFirstName){
        if(userFirstName==null||userFirstName.isEmpty()) {

            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - FirstName is empty"));
        }

        if(userFirstName.length() <= 120  && userFirstName.matches("^[a-zA-Z]*$")){
            return true;
        }else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - FirstName Invalid Format"));
        }
    }

    private boolean verifyUserLastName(String lastName){
        if (lastName==null||lastName.isEmpty()) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - LastName is empty"));
        }

        if(lastName.length() <= 120 && lastName.matches("^[a-zA-Z]*$")){
            return true;
        }else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - LastName Invalid Format"));
        }
    }

    private boolean verifyUserEmailAndPhoneNumber(String email, String phoneNumber){
        boolean emailAndPhoneIsCorrect = false;
        if(phoneNumber != null){
            emailAndPhoneIsCorrect = verifyUserPhoneNumber(phoneNumber);
            if(!emailAndPhoneIsCorrect) {
                return false;
            }
        }

        if (email != null){
            emailAndPhoneIsCorrect = verifyUserEmail(email);
            if(!emailAndPhoneIsCorrect) {
                return false;
            }
        }
        return emailAndPhoneIsCorrect;
    }
    private boolean verifyUserEmail(String email){

        String[] divideEmail = email.split("@");

        //Verify if there is one @, later get the second part with de domain, and verify that the first part not have special characters

        if(divideEmail.length == 2 && divideEmail[0].matches("^[a-zA-Z0-9]*$") && divideEmail[1].equals("icesi.edu.co")){
            return true;
        }else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - Email is not correct"));

        }
    }

    private boolean verifyUserPhoneNumber(String phoneNumber){

        //Verify if have the correct format of a colombian number phone +57XXXXXXXXXX
        if(phoneNumber.substring(0,3).equals("+57") && phoneNumber.substring(3,13).matches("^[0-9]*$") && phoneNumber.length() == 13){
            return true;
        }else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - Phone Invalid Format"));
        }
    }*/
}