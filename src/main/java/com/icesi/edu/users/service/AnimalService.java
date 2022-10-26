package com.icesi.edu.users.service;

import com.icesi.edu.users.model.Animal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface AnimalService {

    public Animal getUser(@PathVariable UUID userId);

    public Animal createUser(@RequestBody Animal userDTO);

    public List<Animal> getUsers();

}
