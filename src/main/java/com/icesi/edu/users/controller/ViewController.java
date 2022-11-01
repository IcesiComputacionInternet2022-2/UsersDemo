package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.ViewAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@AllArgsConstructor
public class ViewController implements ViewAPI {
    @Override
    public String getHomePage(Model model) {
        model.addAttribute("welcome", "Hola");
        return "index";
    }
}
