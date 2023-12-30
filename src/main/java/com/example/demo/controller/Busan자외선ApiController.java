package com.example.demo.controller;


import com.example.demo.domain.entity.BusanZa;
import com.example.demo.domain.repository.BusanZaRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class Busan자외선ApiController {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private BusanZaRepository busanZaRepository;

    @GetMapping("/za")
    public @ResponseBody Root 자외선(){
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
        String time = (now.getHour()-1)+"";
        System.out.println("now.getHour() : " + now.getHour());

        if((now.getHour())<10){
            time = "0" + time;
        }


        String pageNo = "1";
        String numOfRows ="100";
        String resultType="JSON";
        time=nowDate+time;

        System.out.println("time : " + time);


        String url = "https://apis.data.go.kr/1360000/LivingWthrIdxServiceV4/getUVIdxV4?serviceKey=xYZ80mMcU8S57mCCY%2Fq8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru%2FjiRQ41FokE9H4lK0Hhg%3D%3D&pageNo=1&numOfRows=100&dataType=json&areaNo=2635052000&time="+time;


        ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET,null, Root.class);
        System.out.println(response.getBody());
        busanZaRepository.deleteAll();
        response.getBody().getResponse().getBody().getItems().getItem().forEach(item -> {

            BusanZa busanZa = new BusanZa();
            busanZa.setH0(item.getH0());
            busanZa.setH3(item.getH3());
            busanZa.setH6(item.getH6());
            busanZa.setH9(item.getH9());
            busanZa.setH12(item.getH12());
            busanZa.setH15(item.getH15());
            busanZa.setH18(item.getH18());
            busanZa.setH21(item.getH21());
            busanZa.setH24(item.getH24());

            busanZaRepository.save(busanZa);

        });



        return response.getBody();

    }

    @Data
    public static  class Body{
        public String dataType;
        public Items items;
        public int pageNo;
        public int numOfRows;
        public int totalCount;
    }

    @Data
    public static  class Header{
        public String resultCode;
        public String resultMsg;
    }
    @Data
    public  static class Item{
        public String code;
        public String areaNo;
        public String date;
        public String h0;
        public String h3;
        public String h6;
        public String h9;
        public String h12;
        public String h15;
        public String h18;
        public String h21;
        public String h24;
        public String h27;
        public String h30;
        public String h33;
        public String h36;
        public String h39;
        public String h42;
        public String h45;
        public String h48;
        public String h51;
        public String h54;
        public String h57;
        public String h60;
        public String h63;
        public String h66;
        public String h69;
        public String h72;
        public String h75;
    }
    @Data
    public static  class Items{
        public ArrayList<Item> item;
    }
    @Data
    public static  class Response{
        public Header header;
        public Body body;
    }
    @Data
    public static class Root{
        public Response response;
    }

}
