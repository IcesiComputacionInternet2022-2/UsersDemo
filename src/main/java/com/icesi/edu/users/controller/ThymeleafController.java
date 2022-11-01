package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.ThymeleafAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ThymeleafController implements ThymeleafAPI {

    @Override
    public String view(Model model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        model.addAttribute("serverTime", dateFormat.format(new Date(System.currentTimeMillis())));
        return "view";
    }
}
