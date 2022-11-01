package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.ViewAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class ViewController implements ViewAPI {


    @Value("${welcome.message}")
    private String message;


    @Override

    public String getServerTime(Model model) {
        model.addAttribute("time", new Date());
        model.addAttribute("welcome", message);
        return "index.html";
    }
}
