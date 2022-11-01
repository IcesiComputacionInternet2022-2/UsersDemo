package com.icesi.edu.users.controller;


import com.icesi.edu.users.api.ViewAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ViewController implements ViewAPI{

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserController userController;

    @Override
    public String home(Model model) {

        String hello = "Hello World!";

        List<UserDTO> users = userController.getUsers();//Funciona

        model.addAttribute("users",users);
        model.addAttribute("welcome",hello);
        return "home";
    }

    public List<UserDTO> getUsers() {//Funciona
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
