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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BuildingWindController {


    private  String serviceKey = "xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";

    @Autowired
    private LocationRepository locationRepository;

    //REST REQ
    @GetMapping(value = "/realTime/{base_date}/{base_time}" ,  produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody WeatherResponse realTime(
            @PathVariable String base_date,
            @PathVariable String base_time
    ) throws ParseException, java.text.ParseException {

        System.out.println(base_date + " | " + base_time);
        RestTemplate restTemplate = new RestTemplate();

        String pageNo ="1";
        String numOfRows = "10";
        String dataType = "JSON";
        String nx = RealTimeProperties.nx;
        String ny = RealTimeProperties.ny;

        //URL 설정
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=" +serviceKey+
                "&pageNo=" +pageNo+
                "&numOfRows=" +numOfRows+
                "&dataType=" +dataType+
                "&base_date=" +"20231217"+
                "&base_time=" +base_time+
                "&nx=" +nx+
                "&ny="+ny;


        ResponseEntity<WeatherResponse> resp = restTemplate.exchange(url, HttpMethod.GET,null,WeatherResponse.class);

        return resp.getBody();

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
