package com.example.practice.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/view/user")
public class UserController {
    @GetMapping("/login")
    public void login() {
        log.info("GET /view/login");
    }

    @GetMapping("/signup")
    public void signup() {
        log.info("GET /view/user/signup");
    }

    @GetMapping("/findid")
    public void logout() {
        log.info("GET /view/user/logout");
    }

    @GetMapping("/findpw")
    public void findpw() {
        log.info("GET /view/user/findpw");
    }
}
