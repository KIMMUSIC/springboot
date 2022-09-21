package com.example.springboot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TController {

    @GetMapping("/test")
    public String test(){

        return "<h1>test 통과</h1>";
    }
}
