package com.example.linktree.security;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenManager {


    @GetMapping("/check")
    public boolean checkToken(){
        return true;
    }
}
