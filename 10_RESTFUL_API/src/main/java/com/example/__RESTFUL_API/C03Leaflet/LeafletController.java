package com.example.__RESTFUL_API.C03Leaflet;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/map")
public class LeafletController {

    @GetMapping("/01")
    public void map01(){
        log.info("GET /map/01");
    }
}
