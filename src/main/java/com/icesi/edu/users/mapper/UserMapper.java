package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
<<<<<<< Updated upstream
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;
=======
import com.icesi.edu.users.dto.UserPublicDTO;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
>>>>>>> Stashed changes

@Mapper(componentModel = "spring")
public interface UserMapper {


     @Mapping(source = "password", target = "hashedPassword")
     User fromDTO(UserDTO userDTO);
<<<<<<< Updated upstream
     UserDTO fromUser(User user);
}
=======

     @Mapping(source = "hashedPassword", target = "password")

     UserDTO fromUser(User user);

     UserPublicDTO fromPublicUser(User user);

}
>>>>>>> Stashed changes
