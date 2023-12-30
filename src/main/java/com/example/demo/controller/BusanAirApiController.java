package com.example.demo.controller;


import com.example.demo.domain.entity.BusanAir;
import com.example.demo.domain.repository.BusanAirRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
@RequestMapping("/air")
public class BusanAirApiController {

    @Autowired
    private BusanAirRepository busanAirRepository;

    @GetMapping("/busan")
    public @ResponseBody Root test(){
        RestTemplate restTemplate = new RestTemplate();

        // 날짜구하기
        LocalDate currentDate = LocalDate.now();
        // 출력 포맷을 지정하여 YYYY년 MM월 dd일 형식의 문자열로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMdd");
        String nowDate = currentDate.format(formatter);

        LocalDateTime now = LocalDateTime.now();
        //-------------------
        //시간지정
        //-------------------
        //다음날로 넘어갔으면  이전날 마지막 시간으로 지정

        String day = LocalDateTime.now().getDayOfMonth() + "";
        String time = now.getHour()+"";
        System.out.println("now.getHour() : " + now.getHour());

        if((now.getHour())<10){
            time = "0" + time;
        }


        String pageNo = "1";
        String numOfRows ="100";
        String resultType="JSON";
        String controlnumber=nowDate+time;
        String item="pm10"; //미세먼지

        busanAirRepository.deleteAll();

        String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey=xYZ80mMcU8S57mCCY%2Fq8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru%2FjiRQ41FokE9H4lK0Hhg%3D%3D&returnType=json&numOfRows=100&pageNo=1&sidoName=부산&ver=1.0";


        ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET,null, Root.class);


        System.out.println(response);
        System.out.println(response.getBody());

        ArrayList<Item> items =  response.getBody().getResponse().getBody().getItems();
        items.forEach(i -> {
            if(i.getStationName().equals("우동")) {

                BusanAir busanAir = new BusanAir();
                busanAir.setPm10(i.getPm10Value());
                busanAir.setPm25(i.getPm25Value());

                busanAirRepository.save(busanAir);
            }

        });


        return response.getBody();

    }


    @Data

    public static class Body{
        public int totalCount;
        public ArrayList<Item> items;
        public int pageNo;
        public int numOfRows;
    }
    @Data

    public static class  Header{
        public String resultMsg;
        public String resultCode;
    }
    @Data

    public static class Item{
        public String so2Grade;
        public String coFlag;
        public String khaiValue;
        public String so2Value;
        public String coValue;
        public String pm25Flag;
        public String pm10Flag;
        public String o3Grade;
        public String pm10Value;
        public String khaiGrade;
        public String pm25Value;
        public String sidoName;
        public Object no2Flag;
        public String no2Grade;
        public Object o3Flag;
        public String pm25Grade;
        public String so2Flag;
        public String dataTime;
        public String coGrade;
        public String no2Value;
        public String stationName;
        public String pm10Grade;
        public String o3Value;
    }
    @Data

    public static class Response{
        public Body body;
        public Header header;
    }
    @Data

    public static class Root{
        public Response response;
    }

}


