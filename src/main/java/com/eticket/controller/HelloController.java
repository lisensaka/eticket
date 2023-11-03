package com.eticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(("/hello"))
public class HelloController {

    @GetMapping
    public String helloMethod(
            Principal principal
    ){
        return "Hello-World " + principal.getName();
    }
}
