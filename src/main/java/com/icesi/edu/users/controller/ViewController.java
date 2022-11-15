package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class ViewController {

    private final LoginService loginService;

    @RequestMapping
    public String view(Model model){
        model.addAttribute("loginDTO",new LoginDTO());
        return "index";
    }

    @PostMapping
    public String loginSubmit(@ModelAttribute LoginDTO loginDTO, Model model) {
        model.addAttribute("loginDTO", loginDTO);
        return "userProfile";
    }

}
