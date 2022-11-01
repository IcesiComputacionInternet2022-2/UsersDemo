package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.GuiAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class GuiController implements GuiAPI {


    @Override
    public String helloWorld(Model model) {
        return "helloWorld";
    }
}
