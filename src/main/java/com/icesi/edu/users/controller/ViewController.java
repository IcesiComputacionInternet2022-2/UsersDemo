package com.icesi.edu.users.controller;

import com.icesi.edu.users.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ViewController {

    public String displaySaveUser(Model model) {
        model.addAttribute("user", new User());
        return "saveUser.html";
    }

    public String saveStudent(Model model, User user) {
        model.addAttribute("user", user);
        return "displayUser.html";
    }
}