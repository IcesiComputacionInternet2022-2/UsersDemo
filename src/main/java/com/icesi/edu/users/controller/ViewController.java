package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.ViewAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ViewController implements ViewAPI {

    @Override
    public String index(Model model) {
        model.addAttribute("message", "Hello World");
        return "index";
    }

}
