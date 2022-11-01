package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UsersAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Date;


@Controller
public class UsersController implements UsersAPI {

    @Override
    public String getServerTime(Model model) {
        model.addAttribute("Server Time", new Date());
        return "users.html";
    }
}
