package com.example.__RESTFUL_API.C04Kakao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/kakao/channel")
public class KakaoChannelController {

    @GetMapping("/main")
    public void channelMain(){
        log.info("GET /kakao/channel/main");
    }
}
