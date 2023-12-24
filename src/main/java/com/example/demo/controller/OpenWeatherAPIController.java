package com.example.demo.controller;


import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
public class OpenWeatherAPIController {


    @GetMapping("/OPTEST/{lat}/{lon}")
    public OpenWeatherAPIResponse test(@PathVariable String lat ,@PathVariable String lon){
        RestTemplate restTemplate = new RestTemplate();


        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=b7a263e63bfe790ff0081e9b619e7c91";

        ResponseEntity<OpenWeatherAPIResponse> response = restTemplate.exchange(url,HttpMethod.GET,null, OpenWeatherAPIResponse.class);


        System.out.println(response);
        System.out.println(response.getBody());

        return response.getBody();
    }

}


@Data
 class Clouds{
    public int all;
}
@Data
 class Coord{
    public double lon;
    public double lat;
}
@Data
class Main{
    public int temp;
    public double feels_like;
    public int temp_min;
    public int temp_max;
    public int pressure;
    public int humidity;
    public int sea_level;
    public int grnd_level;
}
@Data
 class OpenWeatherAPIResponse{
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}
@Data
 class Sys{
    public String country;
    public int sunrise;
    public int sunset;
}
@Data
 class Weather{
    public int id;
    public String main;
    public String description;
    public String icon;
}
@Data
 class Wind{
    public double speed;
    public int deg;
    public double gust;
}


