package com.icesi.edu.users.api;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserPublicDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
public interface UserAPI {

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable UUID userId);

    @PostMapping()
    public UserDTO createUser(@RequestBody @Valid UserDTO userDTO);

    @GetMapping
    public List<UserPublicDTO> getUsers();

}
