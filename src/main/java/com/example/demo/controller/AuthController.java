package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage() {

        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {

        return "redirect:/";
    }
}


