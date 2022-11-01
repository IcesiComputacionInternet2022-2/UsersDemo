package com.icesi.edu.users.api;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface ViewAPI {

    @GetMapping("/home")
    String home(Model model);

}
