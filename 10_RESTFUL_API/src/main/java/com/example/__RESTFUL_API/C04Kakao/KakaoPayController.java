package com.example.__RESTFUL_API.C04Kakao;


import jdk.jfr.DataAmount;
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

import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/kakao/pay")
public class KakaoPayController {

    @GetMapping("/main")
    public void payMain() {
        log.info("GET /kakao/pay/main");
    }

    @GetMapping("/req")
    @ResponseBody
    public void payReq() {
        log.info("GET /kakao/pay/req");
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        String SECRET_KEY = "DEV793EE02B0072E06E5EB3EAFFDA979A52C4A06";

//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("Authorization","SECRET_KEY "+SECRET_KEY);

//        HTTP 요청 파라미터

        JSONObject obj = new JSONObject();
        obj.put("cid","TC0ONETIME");
        obj.put("partner_order_id","partner_order_id");
        obj.put("partner_user_id","partner_user_id");
        obj.put("item_name","초코파이");
        obj.put("quantity","1");
        obj.put("total_amount","1112200");
        obj.put("vat_amount","200");
        obj.put("tax_free_amount","0");
        obj.put("approval_url","http://localhost:8090/kakao/pay/success");
        obj.put("cancel_url","http://localhost:8090/kakao/pay/cancel");
        obj.put("fail_url","http://localhost:8090/kakao/pay/fail");


//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(obj, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoPayResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity , KakaoPayResponse.class);
        System.out.println(responseEntity.getBody());
    }

    @GetMapping("/success")
    public void paySuccess() {
        log.info("GET /kakao/pay/success");
    }

    @GetMapping("/cancel")
    public void payCancel() {
        log.info("GET /kakao/pay/cancel");
    }

    @GetMapping("/fail")
    public void payFail() {
        log.info("GET /kakao/pay/fail");
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class KakaoPayResponse{
        public String tid;
        public boolean tms_result;
        public Date created_at;
        public String next_redirect_pc_url;
        public String next_redirect_mobile_url;
        public String next_redirect_app_url;
        public String android_app_scheme;
        public String ios_app_scheme;
    }
}
