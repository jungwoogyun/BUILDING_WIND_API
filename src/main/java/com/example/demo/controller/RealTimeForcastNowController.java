package com.example.demo.controller;


import com.example.demo.domain.entity.RealTimeForcastNow;
import com.example.demo.domain.repository.RealTimeForcastNowRepository;
import com.example.demo.domain.repository.RealTimeWindDirectionRepostitory;
import com.example.demo.domain.repository.RealTimeWindNowRepostitory;
import com.example.demo.domain.repository.RealTimeWindPowerRepostitory;
import com.example.demo.properties.RealTimeProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class RealTimeForcastNowController {
    private RestTemplate restTemplate = new RestTemplate();
    public RealTimeForcastNowController(){


    }
    static String nowTime;
    private String serviceKey = "xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";

    private String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";
    //REST REQ

    @Autowired
    private RealTimeForcastNowRepository realTimeForcastNowRepository;

    @GetMapping(value = "/RT_FORCAST_NOW", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody void realTimeForcastNowFunc(){


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

        String day = LocalDateTime.now().getDayOfMonth()+"";
        String time = "";
        System.out.println("now.getHour() : " + now.getHour());

        if(now.getMinute()<45){
            //만약시간이 45분전이라면 시간에서 -1 할것  // 만약시간이 45분이후라면 시간은 그대로 패스
            time = (now.getHour()-1) + "30";
        }else {
            time = now.getHour()+"00";
        }
        //시간이 07~09라면
        if(now.getHour()==7 ||now.getHour()==8||now.getHour()==9 ) {
            time = "0" + time;
        }
        //시간이 01~06시면 이전날짜로 , 마지막 시간대로 변경 -> 스케쥴러로 대체 해보자
//        else if(now.getHour()<=6){
//            day = (LocalDateTime.now().getDayOfMonth()-1) + "";
//            time = "23";
//        }

        //새벽00시라면
        if(now.getHour()<7){
            time="2300";
            // 현재 날짜 얻기
            currentDate = currentDate.minusDays(1);
            nowDate = currentDate.format(formatter);

        }


        System.out.println("TIME : " + time + " DAY : " + day + " NOWDATE : " + nowDate );

        String pageNo = "1";
        String numOfRows = "100";
        String dataType = "JSON";

        String nx = RealTimeProperties.nx;
        String ny = RealTimeProperties.ny;



        realTimeForcastNowRepository.deleteAll();

        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=" + serviceKey +
                "&pageNo=" + pageNo +
                "&numOfRows=" + numOfRows +
                "&dataType=" + dataType +
                "&base_date=" + nowDate +
                "&base_time=" + time +
                "&nx=" + RealTimeProperties.nx +
                "&ny=" + RealTimeProperties.ny;

        ResponseEntity<RealTimeForcastResponses> resp = restTemplate.exchange(url, HttpMethod.GET, null, RealTimeForcastResponses.class);

        resp.getBody().getResponse().getBody().getItems().getItem().forEach(item -> {

            RealTimeForcastNow realTimeForcastNow = new RealTimeForcastNow();
            realTimeForcastNow.setBaseDate(item.getBaseDate());
            realTimeForcastNow.setBaseTime(item.getBaseTime());
            realTimeForcastNow.setFcstDate(item.getFcstDate());
            realTimeForcastNow.setFcstTime(item.getFcstTime());
            realTimeForcastNow.setFcstValue(item.getFcstValue());
            realTimeForcastNow.setCategory(item.getCategory());
            realTimeForcastNow.setNx(item.getNx());
            realTimeForcastNow.setNy(item.getNy());


            realTimeForcastNowRepository.save(realTimeForcastNow);


        });

        System.out.println(resp.getBody());



    }
}

@Data
class RealTimeForcastResponse{
    public RealTimeForcastHeader header;
    public RealTimeForcastBody body;
}
@Data
class RealTimeForcastBody{
    public String dataType;
    public RealTimeForcastItems items;
    public int pageNo;
    public int numOfRows;
    public int totalCount;
}
@Data
class RealTimeForcastHeader{
    public String resultCode;
    public String resultMsg;
}
@Data
class RealTimeForcastItem{
    public String baseDate;
    public String baseTime;
    public String category;
    public String fcstDate;
    public String fcstTime;
    public String fcstValue;
    public int nx;
    public int ny;
}
@Data
class RealTimeForcastItems{
    public ArrayList<RealTimeForcastItem> item;
}
@Data
class RealTimeForcastResponses{
    public RealTimeForcastResponse response;
}


