package com.example.__RESTFUL_API.C05Naver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/naver/search")
public class NaverSearchController {

    private final String CLIENT_ID = "9HXae_RJ7Ok_hSVQXl1S";
    private final String CALLBACK_URL = "http://localhost:8090/naver/callback";
    private final String CLIENT_SECRET_KEY = "ixpOS736V9";

    @GetMapping("/book/{keyword}")
    public String searchBook(@PathVariable("keyword") String keyword) {
        log.info("GET /naver/search/book/keyword : ", keyword);

        String url = "https://openapi.naver.com/v1/search/book.json?query="+keyword;

//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id",CLIENT_ID);
        headers.add("X-Naver-Client-Secret",CLIENT_SECRET_KEY);

//        HTTP 요청 파라미터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("query", keyword);

//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity , String.class);
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }
}
