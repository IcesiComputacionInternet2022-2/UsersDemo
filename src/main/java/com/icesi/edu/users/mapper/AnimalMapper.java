package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.AnimalDTO;
import com.icesi.edu.users.model.Animal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

     Animal fromDTO(AnimalDTO animalDTO);
     AnimalDTO fromAnimal(Animal animal);
}
