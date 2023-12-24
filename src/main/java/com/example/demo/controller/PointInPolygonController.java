package com.example.demo.controller;

import com.example.demo.controller.OpenWeatherAPIResponse;
import com.example.demo.domain.entity.GreenPolygon;
import com.example.demo.domain.repository.GreenPolygonRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/polygon")

public class PointInPolygonController {
    @Autowired
    private GreenPolygonRepository greenPolygonRepository;

    public static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Polygon {
        List<Point> points;

        public Polygon(List<Point> points) {
            this.points = points;
        }
    }

    public static boolean isPointInPolygon(Point point, Polygon polygon) {
        int i, j, nvert = polygon.points.size();
        boolean c = false;

        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            if (((polygon.points.get(i).y > point.y) != (polygon.points.get(j).y > point.y)) &&
                    (point.x < (polygon.points.get(j).x - polygon.points.get(i).x) * (point.y - polygon.points.get(i).y) / (polygon.points.get(j).y - polygon.points.get(i).y) + polygon.points.get(i).x)) {
                c = !c;
            }
        }
        return c;
    }


    @GetMapping("/green")
    @Transactional(rollbackFor = Exception.class)
    public  List<GreenPolygon> CreatePolygonGreen() throws InterruptedException {
        List<Point> polygonCoordinates = new ArrayList<>();
        polygonCoordinates.add(new Point(129.1622554, 35.1583793));
        polygonCoordinates.add(new Point(129.1686927, 35.1590459));
        polygonCoordinates.add(new Point(129.1758167, 35.1558881));
        polygonCoordinates.add(new Point(129.1767179, 35.1684834));
        polygonCoordinates.add(new Point(129.1627275, 35.1682028));
        polygonCoordinates.add(new Point(129.1622554, 35.1583793)); // 마지막 지점을 시작 지점으로 추가하여 폴리곤을 폐포리곤으로 만듦

        Polygon polygon = new Polygon(polygonCoordinates);

        double step = 0.005;//단위(0.001 은 : 146건,, 너무 널널하다..이걸로할까그냥..)

//        첫 번째 소수점 자리는 최대 11.1km
//        두 번째 소수점 자리는 1.1 km
//        세 번째 소수점 자리는 110m
//        네 번째 소수점 자리는 11m
//        다섯 번째 소수점 자리는 1.1 m
//        여섯 번째 소수점 자리는 0.11 m


        List<Point> resultCoordinates = new ArrayList<>();

        for (double lng = 129.15; lng <= 129.18; lng += step) {
            for (double lat = 35.15; lat <= 35.18; lat += step) {
                Point point = new Point(lng, lat);

                if (isPointInPolygon(point, polygon)) {
                    resultCoordinates.add(new Point(lng, lat));
                }
            }
        }

        RestTemplate restTemplate = new RestTemplate();

        List<GreenPolygon> list = new ArrayList<>();

        // 결과 좌표 출력
        for (Point resultCoordinate : resultCoordinates) {
            System.out.println("Longitude: " + resultCoordinate.x + ", Latitude: " + resultCoordinate.y);

            GreenPolygon greenPolygon = new GreenPolygon();
            greenPolygon.setLat(resultCoordinate.y+"");
            greenPolygon.setLon(resultCoordinate.x+"");

//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("lat",resultCoordinate.y);
//            jsonObject.put("lon",resultCoordinate.x);

            String url = "https://api.openweathermap.org/data/2.5/weather?lat="+resultCoordinate.y+"&lon="+resultCoordinate.x+"&appid=b7a263e63bfe790ff0081e9b619e7c91";

            ResponseEntity<OpenWeatherAPIResponse> response = restTemplate.exchange(url,HttpMethod.GET,null, OpenWeatherAPIResponse.class);


//            jsonObject.put("speed", response.getBody().getWind().getSpeed());
//            jsonObject.put("deg", response.getBody().getWind().getDeg());
//            list.add(jsonObject);
            greenPolygon.setSpeed(response.getBody().getWind().getSpeed()+"");
            greenPolygon.setDeg( response.getBody().getWind().getDeg()+"");
            greenPolygon.setLocalDateTime(LocalDateTime.now());
            greenPolygonRepository.save(greenPolygon);

            list.add(greenPolygon);
            Thread.sleep(1000);



        }
        System.out.println("총 좌표 개수 : " + list.size());
        return list;
    }



}
//자바스크립트 코드
//-----------------------------
//POLYGON 내의 모든 좌표 구하기
//-----------------------------
//<!-- Turf.js -->
//<script src="https://unpkg.com/@turf/turf"></script>
// 주어진 좌표로 폴리곤 생성
//<script>
//            const polygonCoordinates = [
//                    [129.1622554, 35.1583793],
//                    [129.1686927, 35.1590459],
//                    [129.1758167, 35.1558881],
//                    [129.1767179, 35.1684834],
//                    [129.1627275, 35.1682028],
//                    [129.1622554, 35.1583793] // 폐포리곤을 위해 시작 지점을 다시 추가
//                    ];
//
//                    const polygon = turf.polygon([polygonCoordinates]);
//
//                    // 소수점 세번째 자리 간격으로 좌표 생성
//                    const step = 0.001; // 0.001은 1/1000에 해당
//                    const resultCoordinates = [];
//
//                    for (let lng = 129.15; lng <= 129.18; lng += step) {
//                    for (let lat = 35.15; lat <= 35.18; lat += step) {
//                    const point = turf.point([lng, lat]);
//
//                    if (turf.booleanPointInPolygon(point, polygon)) {
//                    resultCoordinates.push({ y: lat, _lat: lat, x: lng, _lng: lng });
//                    }
//                    }
//                    }
//
//                    console.log(resultCoordinates);
//</script>