package com.icesi.edu.users.api;

import com.icesi.edu.users.dto.UserNoPassDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


public interface ViewAPI{
    @GetMapping("/view")
    public String getServerTime(Model model);
}
