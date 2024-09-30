package com.JavaApiRest.todosimple.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class LoginController {

    @PostMapping("/login")
    public void login(){
    }
}