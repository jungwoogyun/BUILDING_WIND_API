package com.example.demo.controller;


import com.example.demo.domain.repository.LocationRepository;
import com.example.demo.properties.RealTimeProperties;
import lombok.Data;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class BuildingWindController {


    private  String serviceKey = "xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";

    @Autowired
    private LocationRepository locationRepository;

    //REST REQ
    @GetMapping(value = "/realTime" ,  produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ResponseEntity<WeatherResponse>> realTime(

    ) throws ParseException, java.text.ParseException {

        RestTemplate restTemplate = new RestTemplate();

        // 날짜구하기
        Calendar cal = Calendar.getInstance();
        String year = cal.get(Calendar.YEAR)+"";
        String month = (cal.get(Calendar.MONTH) + 1)+""; // 월은 0부터 시작하므로 1을 더해줌
        if(cal.get(Calendar.MONTH) + 1<10){
            month ="0"+((cal.get(Calendar.MONTH) + 1)+"");
        }
        String day = cal.get(Calendar.DAY_OF_MONTH)+"";
        if(Integer.parseInt(day) <10) {
            day = "0" + day;
        }

        // 시간구하기
        LocalTime now = LocalTime.now();// 현재 시간
        System.out.println(now);   // 현재시간 출력
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH00"); // 포맷 정의하기
        String time = now.format(formatter);      // 포맷 적용하기
        if("0000".equals(time) || "0100".equals(time)|| "0200".equals(time) || "0300".equals(time)||"0400".equals(time)||"0500".equals(time)||"0600".equals(time))
        {
            time = "2300";
            day = (cal.get(Calendar.DAY_OF_MONTH)-1)+"";

        }else{
            time = (Integer.parseInt(time) -100)+"";

        }
        if(Integer.parseInt(day.substring(0,2)) <10){

            day = "0" + day;
        }

        if(time.length()<4)
            time = "0" + time;


        System.out.println("TIME : " + time);
        String nowDate = year+month+day;


        //0000시이면 이전 날짜로 처리



        String addr;
        String pageNo ="1";
        String numOfRows = "10";
        String dataType = "JSON";

        String base_time = "0600";
        String nx = RealTimeProperties.nx;
        String ny = RealTimeProperties.ny;

        //URL 설정
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=" +serviceKey+
                "&pageNo=" +pageNo+
                "&numOfRows=" +numOfRows+
                "&dataType=" +dataType+
                "&base_date=" +nowDate+
                "&base_time=" +base_time+
                "&nx=" +nx+
                "&ny="+ny;



        List<ResponseEntity<WeatherResponse>> list = new ArrayList<>();
        ResponseEntity<WeatherResponse> resp = restTemplate.exchange(url, HttpMethod.GET,null,WeatherResponse.class);
        list.add(resp);

        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        while(true)
        {

            Date date = sdf.parse(base_time);
            // Calendar 객체를 사용하여 시간을 증가
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 1);
            // 변환된 시간을 다시 문자열로 형식화
            base_time = sdf.format(calendar.getTime());
            System.out.println("base_time : "  + base_time);


            url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=" +serviceKey+
                "&pageNo=" +pageNo+
                "&numOfRows=" +numOfRows+
                "&dataType=" +dataType+
                "&base_date=" +nowDate+
                "&base_time=" +base_time+
                "&nx=" +nx+
                "&ny="+ny;
            resp = restTemplate.exchange(url, HttpMethod.GET,null,WeatherResponse.class);
            list.add(resp);


            if(base_time.equals(time))
                break;
        }

        list.forEach(item->System.out.println(item));

        return list;
    }

}


@Data
class WeatherResponse {
    private Response response;
}
@Data
class Response  {
    private Header header;
    private Body body;
}
@Data
class Header{
    private String resultCode;
    private String resultMsg;
}
@Data
class Body {
    private String dataType;
    private Items items;
    private int pageNo;
    private int numOfRows;
    private int totalCount;
}
@Data
class Items {
    private List<WeatherItem> item;

}
@Data
class WeatherItem {
    private String baseDate;
    private String baseTime;
    private String category;
    private int nx;
    private int ny;
    private String obsrValue;
}
