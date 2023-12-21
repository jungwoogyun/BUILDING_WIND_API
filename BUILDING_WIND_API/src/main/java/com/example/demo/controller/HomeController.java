package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "index";
    }
    @GetMapping("/login")
    public void login(){

    }

    @GetMapping("/menu01")
    public void menu01(){}
    @GetMapping("/menu02")
    public void menu02(){}
    @GetMapping("/menu03")
    public void menu03(){}
    @GetMapping("/menu04")
    public void menu04(){}
    @GetMapping("/menu05")
    public void menu05(){}
    @GetMapping("/menu06")
    public void menu06(){}

}
