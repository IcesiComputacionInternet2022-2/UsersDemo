package com.icesi.edu.users.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view")
public interface ThymeleafAPI {

    @GetMapping
    String view(Model model);
}
