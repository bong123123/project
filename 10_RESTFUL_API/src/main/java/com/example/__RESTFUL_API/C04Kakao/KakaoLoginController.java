package com.example.__RESTFUL_API.C04Kakao;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

// REST API KEY 4511d067f367280b79cc19b8f2f588f2
@Controller
@Slf4j
@RequestMapping("/kakao")
public class KakaoLoginController {

    String REDIRECT_URL = "http://localhost:8090/kakao/callback";
    String CLIENT_ID = "4511d067f367280b79cc19b8f2f588f2";
    String RESPONSE_TYPE = "code";
    String LOGOUT_REDIRECT_URL = "http://localhost:8090/kakao/main";

    private KakaoResponse kakaoResponse;


    @GetMapping("/login")
    public String getCode(){
        log.info("GET /kakao/login");
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URL+"&response_type="+RESPONSE_TYPE;
    }


    @GetMapping("/callback")
    public String callback(
            @RequestParam("code") String code
    ){
        log.info("GET /kakao/callback?code="+code);
        String url = "https://kauth.kakao.com/oauth/token";

//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

//        HTTP 요청 파라미터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("redirect_uri",REDIRECT_URL);
        params.add("code",code);

//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity , KakaoResponse.class);
        System.out.println(responseEntity);

        this.kakaoResponse = responseEntity.getBody();

        return "redirect:/kakao/main";

    }

    @GetMapping("/main")
    public void main(){
        log.info("GET /kakao/main");
        // access_token으로 API 요청
    }

    @GetMapping("/profile")
    public void profile(Model model){
        log.info("GET /kakao/profile");
        String url = "https://kapi.kakao.com/v2/user/me";
//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());
//        HTTP 엔티티
        HttpEntity profileEntity = new HttpEntity<>(headers);
//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProfileResponse> response = restTemplate.exchange(url,HttpMethod.POST,profileEntity,ProfileResponse.class);
        System.out.println(response.getBody());
        model.addAttribute("profile",response.getBody());
    }

    @GetMapping("/logout")
    @ResponseBody
    public void logout() {
        log.info("GET /kakao/logout");

        String url = "https://kapi.kakao.com/v1/user/logout";
//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());

//        HTTP 엔티티
        HttpEntity profileEntity = new HttpEntity<>(headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.POST,profileEntity,String.class);
        System.out.println(response.getBody());
    }

    @GetMapping("/unlink")
    @ResponseBody
    public void unlink() {
        log.info("GET /kakao/unlink");
        String url = "https://kapi.kakao.com/v1/user/unlink";
//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());

//        HTTP 엔티티
        HttpEntity profileEntity = new HttpEntity<>(headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.POST,profileEntity,String.class);
        System.out.println(response.getBody());
    }

    @GetMapping("/superLogout")
    public void RealLogout(HttpServletResponse resp) throws IOException {
        log.info("GET /kakao/superLogout");

        resp.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id="+CLIENT_ID+"&logout_redirect_uri="+LOGOUT_REDIRECT_URL);

//        http://localhost:8090/kakao/main
    }

    @GetMapping("/getCodeMsg")
    public String getCodeMsg(){
        log.info("GET /kakao/getCodeMsg");
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URL+"&response_type="+RESPONSE_TYPE+"&scope=talk_message";
    }

    @GetMapping("/message/me/{message}")
    @ResponseBody
    public void message_me(@PathVariable("message") String message){
        log.info("GET /kakao/message/me" + message);
        String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());

//        HTTP 요청 파라미터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        JSONObject template_object = new JSONObject();
        template_object.put("object_type","text");
        template_object.put("text",message);
        template_object.put("link",new JSONObject());
        template_object.put("button_title","응애응애");
        params.add("template_object", template_object.toString());


//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity , String.class);
        System.out.println(responseEntity);

    }
    @GetMapping("/getFriendsAgree")
    public String getFriendsAgree(){
        log.info("GET /kakao/getFriendsAgree");
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URL+"&response_type="+RESPONSE_TYPE+"&scope=friends";
    }

    @GetMapping("/getFriends")
    @ResponseBody
    public void getFriends(){
        log.info("GET /kakao/getFriends");
        String url = "https://kapi.kakao.com/v1/api/talk/friends";

//        HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());

//        HTTP 요청 파라미터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("offset","0");
        params.add("limit","50");
        params.add("order","asc");

//        HTTP 요청 엔티티(헤더+파라미터)
        HttpEntity requestEntity = new HttpEntity<>(params, headers);

//        HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity , String.class);
        System.out.println(responseEntity);

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class KakaoAccount{
        public boolean profile_nickname_needs_agreement;
        public boolean profile_image_needs_agreement;
        public Profile profile;
        public boolean has_email;
        public boolean email_needs_agreement;
        public boolean is_email_valid;
        public boolean is_email_verified;
        public String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Profile{
        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public boolean is_default_image;
        public boolean is_default_nickname;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ProfileResponse{
        public long id;
        public Date connected_at;
        public Properties properties;
        public KakaoAccount kakao_account;
    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class KakaoResponse{
        public String access_token;
        public String token_type;
        public String refresh_token;
        public int expires_in;
        public String scope;
        public int refresh_token_expires_in;
    }
}
