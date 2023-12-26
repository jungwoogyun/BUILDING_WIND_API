package com.example.demo.controller;


import com.example.demo.domain.entity.GreenPolygon;
import com.example.demo.domain.repository.GreenPolygonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


class Point {
    double lon;
    double lat;

    public Point(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }
}

@Controller
@RequestMapping("/gen")
public class WindSpeedDataGenerator {


    @Autowired
    private GreenPolygonRepository greenPolygonRepository;


    @GetMapping("/green")
    public @ResponseBody List<GreenPolygon> getGreenPolygon() {

        // 다각형의 꼭지점 좌표
        List<Point> polygonCoordinates = new ArrayList<>();
        polygonCoordinates.add(new Point(129.1622554, 35.1583793));
        polygonCoordinates.add(new Point(129.1686927, 35.1590459));
        polygonCoordinates.add(new Point(129.1758167, 35.1558881));
        polygonCoordinates.add(new Point(129.1767179, 35.1684834));
        polygonCoordinates.add(new Point(129.1627275, 35.1682028));
        polygonCoordinates.add(new Point(129.1622554, 35.1583793));

        // 데이터 생성
        List<GreenPolygon> dataRows = generateWindSpeedData(polygonCoordinates);

        System.out.println("총개수 : "  + dataRows.size());

        dataRows.forEach(item->{
            greenPolygonRepository.save(item);
        });

        return dataRows;
    }




    private static List<GreenPolygon> generateWindSpeedData(List<Point> polygonCoordinates) {
        List<GreenPolygon> list = new ArrayList<>();
        // 위도, 경도의 간격
        double latStep = 0.0003;    // 0.0008 : 220개 /0.0004 :800개정도        //0.0001 : 14315
        double lonStep = 0.0003;    // 0.0008 : 220개 /0.0004 :800개정도        //0.0001 : 14315

        // 풍속 정보 생성
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



        for (double lat = getMinLat(polygonCoordinates); lat <= getMaxLat(polygonCoordinates); lat += latStep) {
            for (double lon = getMinLon(polygonCoordinates); lon <= getMaxLon(polygonCoordinates); lon += lonStep) {

                Point point = new Point(lon, lat);
                if (isPointWithinPolygon(point, polygonCoordinates)) {
                    int deg = random.nextInt(2) == 0 ? 292 : 293;





                    GreenPolygon greenPolygon = GreenPolygon.builder()
                            .lon(lon+"")
                            .lat(lat+"")
                            .deg(deg+"")
                            .localDateTime(LocalDateTime.now())
                            .build();

                    list.add(greenPolygon);


                }
            }
        }

        // 풍속 설정 100 * 0,0,
        int total = list.size();
        int count =0;
        Set<Integer> set = new HashSet<>();

        int speed80 =  (int)Math.ceil(list.size() * 0.9);
        int speed15 =  (int)Math.ceil(list.size() * 0.08);
        int speed05 =  (int)Math.ceil(list.size() * 0.02);

        Integer[] arr = new Integer[total];

        for(int x=0;x<total;x++){

            int rnd = random.nextInt(total);
            if(set.contains(rnd)){
                System.out.println("와떠뻑");
                x--;
                continue;
            }
            set.add(rnd);
            arr[x] = rnd;

        }
        System.out.println("완료 : " + arr);
        for(int a : arr) System.out.println(a);

        int i=0;
        while(i<total){

            if(i<speed80){
                GreenPolygon greenPolygon =  list.get(arr[i]);
                greenPolygon.setSpeed((random.nextDouble() * 14)+"");
                list.set(arr[i], greenPolygon);
            }else if (i < speed80+speed15 ){
                GreenPolygon greenPolygon =  list.get(arr[i]);
                greenPolygon.setSpeed((14 + random.nextDouble() * 7)+"");
                list.set(arr[i], greenPolygon);
            }else{
                GreenPolygon greenPolygon =  list.get(arr[i]);
                greenPolygon.setSpeed((21 + random.nextDouble() * 9)+"");
                list.set(arr[i], greenPolygon);
            }
            i++;
        }





        return list;
    }

    private static boolean isPointWithinPolygon(Point point, List<Point> polygonCoordinates) {
        int i, j;
        boolean result = false;

        for (i = 0, j = polygonCoordinates.size() - 1; i < polygonCoordinates.size(); j = i++) {
            if ((polygonCoordinates.get(i).lat > point.lat) != (polygonCoordinates.get(j).lat > point.lat) &&
                    (point.lon < (polygonCoordinates.get(j).lon - polygonCoordinates.get(i).lon) * (point.lat - polygonCoordinates.get(i).lat) / (polygonCoordinates.get(j).lat - polygonCoordinates.get(i).lat) + polygonCoordinates.get(i).lon)) {
                result = !result;
            }
        }



        return result;
    }




    private static double getMinLat(List<Point> points) {
        return points.stream().mapToDouble(p -> p.lat).min().orElse(0);
    }

    private static double getMaxLat(List<Point> points) {
        return points.stream().mapToDouble(p -> p.lat).max().orElse(0);
    }

    private static double getMinLon(List<Point> points) {
        return points.stream().mapToDouble(p -> p.lon).min().orElse(0);
    }

    private static double getMaxLon(List<Point> points) {
        return points.stream().mapToDouble(p -> p.lon).max().orElse(0);
    }

}




