package com.example.demo.controller;


import com.example.demo.domain.entity.RealTimeWindDirection;
import com.example.demo.domain.entity.RealTimeWindNow;
import com.example.demo.domain.entity.RealTimeWindPower;

import com.example.demo.domain.repository.RealTimeWindDirectionRepostitory;
import com.example.demo.domain.repository.RealTimeWindNowRepostitory;
import com.example.demo.domain.repository.RealTimeWindPowerRepostitory;
import com.example.demo.properties.RealTimeProperties;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class RealTimeWindPowerAndDirectionController {
    //06시 발표(정시단위) //-매시각 40분 이후 호출

    private RestTemplate restTemplate = new RestTemplate();
    public RealTimeWindPowerAndDirectionController(){

    }
    static String nowTime;
    private String serviceKey = "xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";


    //REST REQ

    @Autowired
    private RealTimeWindPowerRepostitory realTimeWindPowerRepostitory;
    @Autowired
    private RealTimeWindDirectionRepostitory realTimeWindDirectionRepostitory;

    @Autowired
    private RealTimeWindNowRepostitory realTimeWindNowRepostitory;


    @GetMapping(value = "/RTNOW", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody void realTime(


    ) throws Exception {

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


        System.out.println("TIME : " + time + " DAY : " + day + " NOWDATE : " + nowDate +" MINUTES : " + now.getMinute());

        String pageNo = "1";
        String numOfRows = "10";
        String dataType = "JSON";

        String nx = RealTimeProperties.nx;
        String ny = RealTimeProperties.ny;


            //URL 설정
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=" + serviceKey +
                    "&pageNo=" + pageNo +
                    "&numOfRows=" + numOfRows +
                    "&dataType=" + dataType +
                    "&base_date=" + nowDate +
                    "&base_time=" + time +
                    "&nx=" + nx +
                    "&ny=" + ny;


            //날짜시간 같은 데이터 있으면 삭제
            realTimeWindDirectionRepostitory.deleteAllBaseDateAndBaseTime(nowDate,time);
            realTimeWindPowerRepostitory.deleteAllBaseDateAndBaseTime(nowDate,time);
            realTimeWindNowRepostitory.deleteAll();

            //요청보내기
            ResponseEntity<WeatherResponse> resp = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);

            //------------------------------------------

            //------------------------------------------

           List<WeatherItem> items =  resp.getBody().getResponse().getBody().getItems().getItem();
           System.out.println("ITEM : " + items);
           items.forEach(item -> {
               //풍속 저장


               if (item.getCategory().equals("WSD")) {

                   RealTimeWindPower realTimeWindPower = new RealTimeWindPower();
                   realTimeWindPower.setBaseTime(item.getBaseTime());
                   realTimeWindPower.setBaseDate(item.getBaseDate());
                   realTimeWindPower.setObsrValue(item.getObsrValue());
                   realTimeWindPower.setCategory(item.getCategory());
                   realTimeWindPower.setNx(item.getNx());
                   realTimeWindPower.setNy(item.getNy());
                   realTimeWindPowerRepostitory.save(realTimeWindPower);
               }

               //풍향
               if (item.getCategory().equals("VEC")) {
                   RealTimeWindDirection realTimeWindDirection = new RealTimeWindDirection();
                   realTimeWindDirection.setBaseTime(item.getBaseTime());
                   realTimeWindDirection.setBaseDate(item.getBaseDate());
                   realTimeWindDirection.setObsrValue(item.getObsrValue());
                   realTimeWindDirection.setCategory(item.getCategory());
                   realTimeWindDirection.setNx(item.getNx());
                   realTimeWindDirection.setNy(item.getNy());
                   realTimeWindDirectionRepostitory.save(realTimeWindDirection);
               }

               //현재시간 기준 모든 정보 저장
               RealTimeWindNow realTimeWindNow = new RealTimeWindNow();
               realTimeWindNow.setBaseTime(item.getBaseTime());
               realTimeWindNow.setBaseDate(item.getBaseDate());
               realTimeWindNow.setObsrValue(item.getObsrValue());
               realTimeWindNow.setCategory(item.getCategory());
               realTimeWindNow.setNx(item.getNx());
               realTimeWindNow.setNy(item.getNy());
               realTimeWindNowRepostitory.save(realTimeWindNow);

           });






    }
}


