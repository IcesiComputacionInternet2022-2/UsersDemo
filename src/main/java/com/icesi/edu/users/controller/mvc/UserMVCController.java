package com.icesi.edu.users.controller.mvc;

import com.icesi.edu.users.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserMVCController {

    @GetMapping("/thymeleaf")
    public String index(Model model){
        model.addAttribute("serverTime", LocalDate.now());
        List<UserDTO> users = new ArrayList<UserDTO>();
        users.add(UserDTO.builder().firstName("Camila").lastName("Rivera").gender("F").build());
        users.add(UserDTO.builder().firstName("Carlos").lastName("Pantoja").gender("M").build());
        users.add(UserDTO.builder().firstName("Carolina").lastName("Hernández").gender("F").build());
        model.addAttribute("users", users);
        model.addAttribute("user", new UserDTO());
        return "index";
    }
    @PostMapping("/saveUser")
    public String validation(@ModelAttribute("user") UserDTO user, BindingResult errors, Model model){
        System.out.println(user);
        return "redirect:/thymeleaf";
    }
}
