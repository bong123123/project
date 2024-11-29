package com.example.__RESTFUL_API.C06Google;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/google")
public class GoogleController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/mail/req")
    @ResponseBody
    public void req(
            @RequestParam("email") String email,
            @RequestParam("text") String text
    ){
        log.info("GET /google/mail/req email : " + email + ", text : " + text);
        log.info("javaMailSender : " + javaMailSender);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);   //전달받는 사람
        message.setSubject("메ㅏㅈㄷㄱ"); //제목
        message.setText(text);  //내용

        javaMailSender.send(message);   //전송
    }
}
