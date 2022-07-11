package com.findme.findme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String home() {
        return "index";
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }
}
