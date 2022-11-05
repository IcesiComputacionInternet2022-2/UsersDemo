package com.icesi.edu.users.controller;

import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/viewUsers")
public class UserViewController {

    private final UserService userService;

    @GetMapping()
    public String getUsers(@ModelAttribute User user, Model model) {
        List<User> users = userService.getUsers().stream().collect(Collectors.toList());
        model.addAttribute("users", users);
        return "index.html";
    }
}
