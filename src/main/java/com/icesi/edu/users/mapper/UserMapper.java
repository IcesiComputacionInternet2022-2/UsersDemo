package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserDTO fromUser(User user);
     UserCreateDTO CreateUser(User user);
     User fromDTO(UserCreateDTO userCreateDTO);

}
