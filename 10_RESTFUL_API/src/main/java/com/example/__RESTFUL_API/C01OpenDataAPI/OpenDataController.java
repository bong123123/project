package com.example.__RESTFUL_API.C01OpenDataAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;

@Controller
@RequestMapping("/opendata")
@Slf4j
public class OpenDataController {

    @GetMapping(value="/food",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<맛집Root> food(){
        log.info("Food API accessed");

        String url = "https://api.odcloud.kr/api/15052653/v1/uddi:af9a76e4-c949-4712-91c5-ecdb41d669fe_201809211854";
        String pageNo = "1";
        String perPage = "100";
        String returnType = "json";
        String serviceKey = "xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";

        url += "?pageNo=" + pageNo;
        url += "&perPage=" + perPage;
        url += "&returnType=" + returnType;
        url += "&serviceKey=" + serviceKey;
        System.out.println(url);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<맛집Root> response =
                restTemplate.exchange(url, HttpMethod.GET,null,맛집Root.class);
        System.out.println("response : " + response.getBody());

        return response;

    }
//    맛집정보
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class 맛집Datum{
        public String 업소명;
        public String 연번;
        public String 전화번호;
        public String 주메뉴;
        public String 주소;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class 맛집Root{
        public int currentCount;
        public ArrayList<맛집Datum> data;
        public int matchCount;
        public int page;
        public int perPage;
        public int totalCount;
    }


    @GetMapping("/weather")
    @ResponseBody
    public ResponseEntity<WeatherRoot> weather(){
        //http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst
        //?serviceKey=인증키&numOfRows=10&pageNo=1
        //&base_date=20210628&base_time=0600&nx=55&ny=127
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String numOfRows="10";
        String pageNo = "1";
        String base_date = "20241125";
        String base_time = "0600";
        String nx = "55";
        String ny = "127";
        String dataType = "json";
        String serviceKey = "xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";

        url+="?numOfRows="+numOfRows;
        url+="&pageNo="+pageNo;
        url+="&base_date="+base_date;
        url+="&base_time="+base_time;
        url+="&nx="+nx;
        url+="&ny="+ny;
        url+="&dataType="+dataType;
        url+="&serviceKey="+serviceKey;
        System.out.println(url);

        // Request Header 설정 x
        // Request Parameter 설정 x
        // Request Entity 로 묶기 x

        // 요청 -> 응답 처리
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherRoot> response =
                restTemplate.exchange(url, HttpMethod.GET,null,WeatherRoot.class);

        System.out.println("response" + response.getBody());

        return response;
    }
//    날짜정보
    @Data
    private static class Body{
        public String dataType;
        public Items items;
        public int pageNo;
        public int numOfRows;
        public int totalCount;
    }
    @Data
    private static  class Header{
        public String resultCode;
        public String resultMsg;
    }
    @Data
    private static  class Item{
        public String baseDate;
        public String baseTime;
        public String category;
        public int nx;
        public int ny;
        public String obsrValue;
    }
    @Data
    private static  class Items{
        public ArrayList<Item> item;
    }
    @Data
    private static  class Response{
        public Header header;
        public Body body;
    }
    @Data
    private static  class WeatherRoot{
        public Response response;
    }

    @GetMapping("/bus")
    @ResponseBody
    public String bus(){
        String url = "https://apis.data.go.kr/6270000/dbmsapi/basic?serviceKey=xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET,null,String.class);

        System.out.println("response" + response.getBody());

        return null;
    }
}
