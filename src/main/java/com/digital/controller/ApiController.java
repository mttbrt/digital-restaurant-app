package com.digital.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class ApiController {

    @GetMapping("/hello")
    public String getGreetings() {
        return "Hello World";
    }

    @GetMapping("/home")
    public String getHome() {
        return "Home";
    }

}
