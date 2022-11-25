package com.icesi.edu.users.api;

import com.icesi.edu.users.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
public interface UserAPI {

    @GetMapping("/{userId}")
    UserDTO getUser(@PathVariable UUID userId);

    @PostMapping()
    UserDTO createUser(@RequestBody UserDTO userDTO);

    @GetMapping
    List<UserDTO> getUsers();

}
