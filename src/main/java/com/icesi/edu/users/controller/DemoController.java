package com.icesi.edu.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/demo")
    public String signup(){
        System.out.println("it works");
        return "form";
    }
}
