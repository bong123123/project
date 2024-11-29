package com.example.practice.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/view")
public class IndexController {
    @GetMapping("/index")
    public void index(){
        log.info("GET /view/index");
    }

}
