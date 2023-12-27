package com.example.demo.controller;


import com.example.demo.domain.entity.CentumPolygon;
import com.example.demo.domain.entity.LctPolygon;
import com.example.demo.domain.entity.MarinPolygon;
import com.example.demo.domain.repository.CentumPolygonRepository;
import com.example.demo.domain.repository.LctPolygonRepository;
import com.example.demo.domain.repository.MarinPolygonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


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
    // 0.0008 : 220개   /0.0004 :800개정도        //0.0001 : 14315        0.0005? = 550개      0.0002 : 3560
    //sample lat,lonRatio : 0.0002 / green red blue
    private static final double latStepRatio = 0.0002;
    private static final double lonStepRatio = 0.0002;

    //greenRatio
    private static final double greenRatio =   0.97;
    //blueRatio
    private static final double blueRatio = 0.025;
    //RedRatio
    private static final double redRatio =  0.005;

    @Autowired
    private LctPolygonRepository lctPolygonRepository;

    @Autowired
    private MarinPolygonRepository marinPolygonRepository;

    @Autowired
    private CentumPolygonRepository centumPolygonRepository;

    @GetMapping("/lct")
    public @ResponseBody List<LctPolygon> getLctPolygon() {

        // 다각형의 꼭지점 좌표(LCT)
        List<Point> polygonCoordinates = new ArrayList<>();
        polygonCoordinates.add(new Point(129.1622554, 35.1583793)); //마지막은 처음 위경도와같게
        polygonCoordinates.add(new Point(129.1686927, 35.1590459));
        polygonCoordinates.add(new Point(129.1758167, 35.1558881));
        polygonCoordinates.add(new Point(129.1767179, 35.1684834));
        polygonCoordinates.add(new Point(129.1627275, 35.1682028));
        polygonCoordinates.add(new Point(129.1622554, 35.1583793)); //마지막은 처음 위경도와같게


        // 데이터 생성
        List<LctPolygon> dataRows = generateLctWindSpeedData(polygonCoordinates);

        System.out.println("총개수 : "  + dataRows.size());
        lctPolygonRepository.deleteAll();

        dataRows.forEach(item->{
            lctPolygonRepository.save(item);
        });

        return dataRows;
    }


    @GetMapping("/marin")
    public @ResponseBody List<MarinPolygon> getMarinPolygon() {

        // 다각형의 꼭지점 좌표(MARINE)
        List<Point> polygonCoordinates = new ArrayList<>();
        polygonCoordinates.add(new Point(129.1338455, 35.1631684));
        polygonCoordinates.add(new Point(129.1382657, 35.1657645));
        polygonCoordinates.add(new Point(129.1393815, 35.1683255));
        polygonCoordinates.add(new Point(129.1424714, 35.1643261));
        polygonCoordinates.add(new Point(129.1494666, 35.160116));
        polygonCoordinates.add(new Point(129.1527282, 35.1574144));
        polygonCoordinates.add(new Point(129.1544877, 35.1564671));
        polygonCoordinates.add(new Point(129.1546165, 35.1547127));
        polygonCoordinates.add(new Point(129.1529857, 35.1517653));
        polygonCoordinates.add(new Point(129.1507112, 35.1514846));
        polygonCoordinates.add(new Point(129.1502391, 35.1526425));
        polygonCoordinates.add(new Point(129.1503249, 35.1544671));
        polygonCoordinates.add(new Point(129.1511833, 35.1562916));
        polygonCoordinates.add(new Point(129.1522561, 35.1569934));
        polygonCoordinates.add(new Point(129.1515266, 35.1572039));
        polygonCoordinates.add(new Point(129.1499816, 35.1569583));
        polygonCoordinates.add(new Point(129.1484367, 35.1564671));
        polygonCoordinates.add(new Point(129.1474496, 35.1550987));
        polygonCoordinates.add(new Point(129.1469775, 35.1536952));
        polygonCoordinates.add(new Point(129.1460334, 35.1532741));
        polygonCoordinates.add(new Point(129.1417419, 35.1547127));
        polygonCoordinates.add(new Point(129.1338455, 35.1631684));

        marinPolygonRepository.deleteAll();
        // 데이터 생성
        List<MarinPolygon> dataRows = generateMarinWindSpeedData(polygonCoordinates);

        System.out.println("총개수 : "  + dataRows.size());

        dataRows.forEach(item->{
            marinPolygonRepository.save(item);
        });

        return dataRows;
    }

    @GetMapping("/centum")
    public @ResponseBody List<CentumPolygon> getCentumPolygon() {

        // 다각형의 꼭지점 좌표(LCT)
        List<Point> polygonCoordinates = new ArrayList<>();
        polygonCoordinates.add(new Point(129.1199534, 35.1805158)); //마지막은 처음 위경도와같게
        polygonCoordinates.add(new Point(129.1222708, 35.1817435));
        polygonCoordinates.add(new Point(129.1256227, 35.1772272));
        polygonCoordinates.add(new Point(129.1222538, 35.1754207));
        polygonCoordinates.add(new Point(129.1199534, 35.1805158)); //마지막은 처음 위경도와같게

        // 데이터 생성
        List<CentumPolygon> dataRows = generateCentumWindSpeedData(polygonCoordinates);

        System.out.println("총개수 : "  + dataRows.size());
        centumPolygonRepository.deleteAll();
        dataRows.forEach(item->{
            centumPolygonRepository.save(item);
        });

        return dataRows;
    }

    private static List<LctPolygon> generateLctWindSpeedData(List<Point> polygonCoordinates) {
        List<LctPolygon> list = new ArrayList<>();
        // 위도, 경도의 간격
        double latStep = latStepRatio;
        double lonStep = lonStepRatio;    // 0.0008 : 220개 /0.0004 :800개정도        //0.0001 : 14315        0.0005?

        // 풍속 정보 생성
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



        for (double lat = getMinLat(polygonCoordinates); lat <= getMaxLat(polygonCoordinates); lat += latStep) {
            for (double lon = getMinLon(polygonCoordinates); lon <= getMaxLon(polygonCoordinates); lon += lonStep) {

                Point point = new Point(lon, lat);
                if (isPointWithinPolygon(point, polygonCoordinates)) {
                    int deg = random.nextInt(2) == 0 ? 292 : 293;


                    LctPolygon lctPolygon = new LctPolygon();
                    lctPolygon.setLon(lon+"");
                    lctPolygon.setLat(lat+"");
                    lctPolygon.setDeg(deg+"");
                    lctPolygon.setLocalDateTime(LocalDateTime.now());
                    list.add(lctPolygon);


                }
            }
        }

        // 풍속 설정 100 * 0,0,
        int total = list.size();
        int count =0;
        Set<Integer> set = new HashSet<>();

        int speed80 =  (int)Math.ceil(list.size() * greenRatio);
        int speed15 =  (int)Math.ceil(list.size() * blueRatio);
        int speed05 =  (int)Math.ceil(list.size() * redRatio);

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
                LctPolygon lctPolygon =  list.get(arr[i]);
                lctPolygon.setSpeed((random.nextDouble() * 14)+"");
                list.set(arr[i], lctPolygon);
            }else if (i < speed80+speed15 ){
                LctPolygon lctPolygon =  list.get(arr[i]);
                lctPolygon.setSpeed((14 + random.nextDouble() * 7)+"");
                list.set(arr[i], lctPolygon);
            }else{
                LctPolygon lctPolygon =  list.get(arr[i]);
                lctPolygon.setSpeed((21 + random.nextDouble() * 9)+"");
                list.set(arr[i], lctPolygon);
            }
            i++;
        }

        return list;
    }



    private static List<MarinPolygon> generateMarinWindSpeedData(List<Point> polygonCoordinates) {
        List<MarinPolygon> list = new ArrayList<>();
        // 위도, 경도의 간격
        double latStep = latStepRatio;    // 0.0008 : 220개 /0.0004 :800개정도        //0.0001 : 14315        0.0005?
        double lonStep = lonStepRatio;    // 0.0008 : 220개 /0.0004 :800개정도        //0.0001 : 14315        0.0005?

        // 풍속 정보 생성
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



        for (double lat = getMinLat(polygonCoordinates); lat <= getMaxLat(polygonCoordinates); lat += latStep) {
            for (double lon = getMinLon(polygonCoordinates); lon <= getMaxLon(polygonCoordinates); lon += lonStep) {

                Point point = new Point(lon, lat);
                if (isPointWithinPolygon(point, polygonCoordinates)) {
                    int deg = random.nextInt(2) == 0 ? 292 : 293;


                    MarinPolygon marginPolygon = new MarinPolygon();
                    marginPolygon.setLon(lon+"");
                    marginPolygon.setLat(lat+"");
                    marginPolygon.setDeg(deg+"");
                    marginPolygon.setLocalDateTime(LocalDateTime.now());
                    list.add(marginPolygon);


                }
            }
        }

        // 풍속 설정 100 * 0,0,
        int total = list.size();
        int count =0;
        Set<Integer> set = new HashSet<>();

        int speed80 =  (int)Math.ceil(list.size() * greenRatio);
        int speed15 =  (int)Math.ceil(list.size() * blueRatio);
        int speed05 =  (int)Math.ceil(list.size() * redRatio);

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
                MarinPolygon marinPolygon =  list.get(arr[i]);
                marinPolygon.setSpeed((random.nextDouble() * 14)+"");
                list.set(arr[i], marinPolygon);
            }else if (i < speed80+speed15 ){
                MarinPolygon marinPolygon =  list.get(arr[i]);
                marinPolygon.setSpeed((14 + random.nextDouble() * 7)+"");
                list.set(arr[i], marinPolygon);
            }else{
                MarinPolygon marinPolygon =  list.get(arr[i]);
                marinPolygon.setSpeed((21 + random.nextDouble() * 9)+"");
                list.set(arr[i], marinPolygon);
            }
            i++;
        }

        return list;
    }

    private static List<CentumPolygon> generateCentumWindSpeedData(List<Point> polygonCoordinates) {
        List<CentumPolygon> list = new ArrayList<>();
        // 다각형의 꼭지점 좌표(MARINE)


        // 위도, 경도의 간격
        double latStep = latStepRatio;    // 0.0008 : 220개 /0.0004 :800개정도        //0.0001 : 14315        0.0005?
        double lonStep = lonStepRatio;    // 0.0008 : 220개 /0.0004 :800개정도        //0.0001 : 14315        0.0005?

        // 풍속 정보 생성
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



        for (double lat = getMinLat(polygonCoordinates); lat <= getMaxLat(polygonCoordinates); lat += latStep) {
            for (double lon = getMinLon(polygonCoordinates); lon <= getMaxLon(polygonCoordinates); lon += lonStep) {

                Point point = new Point(lon, lat);
                if (isPointWithinPolygon(point, polygonCoordinates)) {
                    int deg = random.nextInt(2) == 0 ? 292 : 293;


                    CentumPolygon centumPolygon = new CentumPolygon();
                    centumPolygon.setLon(lon+"");
                    centumPolygon.setLat(lat+"");
                    centumPolygon.setDeg(deg+"");
                    centumPolygon.setLocalDateTime(LocalDateTime.now());
                    list.add(centumPolygon);


                }
            }
        }

        // 풍속 설정 100 * 0,0,
        int total = list.size();
        int count =0;
        Set<Integer> set = new HashSet<>();

        int speed80 =  (int)Math.ceil(list.size() * greenRatio);
        int speed15 =  (int)Math.ceil(list.size() * blueRatio);
        int speed05 =  (int)Math.ceil(list.size() * redRatio);

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
                CentumPolygon centumPolygon =  list.get(arr[i]);
                centumPolygon.setSpeed((random.nextDouble() * 14)+"");
                list.set(arr[i], centumPolygon);
            }else if (i < speed80+speed15 ){
                CentumPolygon centumPolygon =  list.get(arr[i]);
                centumPolygon.setSpeed((14 + random.nextDouble() * 7)+"");
                list.set(arr[i], centumPolygon);
            }else{
                CentumPolygon centumPolygon =  list.get(arr[i]);
                centumPolygon.setSpeed((21 + random.nextDouble() * 9)+"");
                list.set(arr[i], centumPolygon);
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




