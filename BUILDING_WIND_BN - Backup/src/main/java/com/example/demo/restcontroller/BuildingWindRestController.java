//package com.example.demo.restcontroller;
//
//
//import com.example.demo.domain.repository.WeatherRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Slf4j
//public class BuildingWindRestController {
//    private final WeatherRepository weatherRepository;
//
//    private  String serviceKey = "xYZ80mMcU8S57mCCY%2Fq8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru%2FjiRQ41FokE9H4lK0Hhg%3D%3D";
//    private String addr;
//    private  String pageNo;
//    private  String numOfRows;
//    private  String dataType;
//    private  String base_date;
//    private  String base_time;
//    private  String nx;
//    private  String ny;
//
//    public BuildingWindRestController(WeatherRepository weatherRepository) {
//        this.weatherRepository = weatherRepository;
//    }
//
//
//
//
//    @GetMapping("/test")
//    public void TEST(){
//
//
//    }
//
//
//    //초단기 실황조회
//    @GetMapping(value="/OpenAPI1/{addr1}/{addr2}/{addr3}",produces= MediaType.APPLICATION_JSON_VALUE)
//    public String 초단기실황조회(
//            @PathVariable String addr1,
//            @PathVariable String addr2,
//            @PathVariable String addr3,
//            Model model
//    ) throws Exception{
//
//        log.info("URL : /OpenAPI1..." );
////        Calendar cal = Calendar.getInstance();
////
//////       날짜구하기
////        String year = cal.get(Calendar.YEAR)+"";
////        String month = (cal.get(Calendar.MONTH) + 1)+""; // 월은 0부터 시작하므로 1을 더해줌
////        if(cal.get(Calendar.MONTH) + 1<10){
////            month ="0"+((cal.get(Calendar.MONTH) + 1)+"");
////        }
////        String day = cal.get(Calendar.DAY_OF_MONTH)+"";
////        if(Integer.parseInt(day) <10)
////            day="0"+day;
////
//////       시간구하기
////        LocalTime now = LocalTime.now();// 현재 시간
////        System.out.println(now);   // 현재시간 출력
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH00"); // 포맷 정의하기
////        String time= now.format(formatter);      // 포맷 적용하기
////        if("0000".equals(time)){
////            time = "2300";
////        }else{
////            time = (Integer.parseInt(time) -100)+"";
////        }
////        System.out.println("time : " + time);         // 포맷 적용된 현재 시간 출력
////
////
////
////        this.pageNo = "1";
////        this.numOfRows="10";
////        dataType="json";
////
////        this.base_date=year+month+"03";
////        System.out.println(year+month+day);
////        this.base_time=time;
////        System.out.println(addr1 + " " + addr2 + " " + addr3);
////        //DB로부터 주소에 대한 격자 가져옴
////        List<Weather> list=weatherRepository.findByAddr1LikeAndAddr2AndAddr3("%"+addr1+"%",addr2,addr3);
////        System.out.println(list);
////        Weather weather = list.get(0);
////
////
////        nx=weather.getNx()+"";
////        ny=weather.getNy()+"";
////
////        //URL 설정
////        addr = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=" +serviceKey+
////                "&pageNo=" +pageNo+
////                "&numOfRows=" +numOfRows+
////                "&dataType=" +dataType+
////                "&base_date=" +base_date+
////                "&base_time=" +base_time+
////                "&nx=" +nx+
////                "&ny="+ny;
////
////        URL url = new URL(addr);
////
////        //서버->JAVA 방향 스트림 객체
////        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
////
////        StringBuffer buff = new StringBuffer(); //데이터 저장용
////
////        //데이터 가져오기
////        String cnt = null;
////        while(true)
////        {
////            cnt = in.readLine();
////            if(cnt==null)
////                break;
////            //System.out.println(cnt);
////            buff.append(cnt);
////        }
////        System.out.println(buff.toString());
////
////        //String ->JSON 변환
////        JSONParser parser = new JSONParser();
////        JSONObject dataObject= (JSONObject)parser.parse(buff.toString());
////        //response 추출
////        JSONObject responseObject= (JSONObject)dataObject.get("response");
////        System.out.println(responseObject);
////        //bodyObject추출
////        JSONObject bodyObject= (JSONObject)responseObject.get("body");
////        JSONObject itemsObject= (JSONObject)bodyObject.get("items");
////        System.out.println(itemsObject);
////        //obsrValue 추출
////
////
////        return itemsObject.toString();
//
//
//        return null;
//    }
//
//
//}
