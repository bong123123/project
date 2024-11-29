package com.example.__RESTFUL_API.C05Naver;

import com.example.__RESTFUL_API.C04Kakao.KakaoLoginController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/naver")
public class NaverLoginController {

    private final String CLIENT_ID = "9HXae_RJ7Ok_hSVQXl1S";
    private final String CALLBACK_URL = "http://localhost:8090/naver/callback";
    private final String CLIENT_SECRET_KEY = "ixpOS736V9";
    private NaverTokenResponse naverTokenResponse;

    @GetMapping("/login")
    public String login_post(){
        log.info("POST /naver/login");
        return "redirect:https://nid.naver.com/oauth2.0/authorize?client_id="+CLIENT_ID+"&response_type=code&redirect_uri="+CALLBACK_URL;
    }

    @GetMapping("/callback")
    @ResponseBody
    public void naverCallback(@RequestParam("code") String code, @RequestParam("state") String state){
        log.info("GET /naver/callback || code : "+code + "|| state : "+state);

        String url = "https://nid.naver.com/oauth2.0/token";

//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();

//        HTTP 요청 파라미터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("client_secret",CLIENT_SECRET_KEY);
        params.add("code",code);
        params.add("state",state);

//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NaverTokenResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity , NaverTokenResponse.class);
        System.out.println(responseEntity.getBody());

        this.naverTokenResponse = responseEntity.getBody();
    }

    @GetMapping("/unlink")
    public String naverUnlink(){
        log.info("GET /naver/unlink");
        return "redirect:https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET_KEY+"&access_token="+naverTokenResponse.getAccess_token();
    }

    @GetMapping("/disconnect")
    public String disconn(){
        log.info("GET /naver/disconnect");
//        return "redirect:https://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/";
        return "redirect:https://nid.naver.com/nidlogin.logout?returl=http://localhost:8090/naver/login";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class NaverTokenResponse{
        public String access_token;
        public String refresh_token;
        public String token_type;
        public String expires_in;
    }



}
