package com.example.__RESTFUL_API.C07PortOne;

import com.example.__RESTFUL_API.C04Kakao.KakaoLoginController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/portOne")
public class PortOneController {

    private TokenResponse tokenResponse;
    String Val1 = "imp64271607";
    String Val2 = "INIpayTest";

    @GetMapping("/main")
    public void main(){
        log.info("GET /portOne/main");
    }

    @GetMapping("/getToken")
    @ResponseBody
    public void getToken(){
        log.info("GET /portOne/getToken");

        String url = "https://api.iamport.kr/users/getToken?imp_uid[]="+Val1+"&imp_uid[]="+Val2+"&merchant_uid[]=merchant_143434085216";

//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type","application/json");

//        HTTP 요청 파라미터
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("imp_key","0227002342443766");
        params.add("imp_secret","N87vijGMOquRT1YEKrn5pdzU9bsjdfrIJkfiHzR3Vxu0c50iFFASEeBXskYmsPJqNxyvDBW8NdxTcP8Q");

//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(params, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity , TokenResponse.class);
        System.out.println(responseEntity.getBody());
        this.tokenResponse = responseEntity.getBody();
    }
    @GetMapping("/getPayments")
    @ResponseBody
    public void getData(){
        log.info("GET /portOne/getPayments");

        String url = "https://api.iamport.kr/payments";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("Authorization","Bearer "+tokenResponse.getResponse().getAccess_token());

//        HTTP 요청 파라미터
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();


//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(params, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity , String.class);
        System.out.println(responseEntity.getBody());
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Response{
        public String access_token;
        public int now;
        public int expired_at;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TokenResponse{
        public int code;
        public Object message;
        public Response response;
    }




}
