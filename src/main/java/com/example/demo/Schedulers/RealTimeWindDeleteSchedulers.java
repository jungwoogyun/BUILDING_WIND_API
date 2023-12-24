package com.example.demo.Schedulers;


import com.example.demo.domain.repository.RealTimeErrorRepository;
import com.example.demo.domain.repository.RealTimeWindDirectionRepostitory;
import com.example.demo.domain.repository.RealTimeWindNowRepostitory;
import com.example.demo.domain.repository.RealTimeWindPowerRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RealTimeWindDeleteSchedulers {


    @Autowired
    private RealTimeWindDirectionRepostitory realTimeWindDirectionRepost;

    @Autowired
    private RealTimeErrorRepository realTimeErrorRepository;

    @Autowired
    private RealTimeWindPowerRepostitory realTimeWindPowerRepostitory;

//    @Autowired
//    private RealTimeWindNowRepostitory realTimeWindNowRepostitory;
//    @Scheduled(cron = "0 */45 * * * *")	    //0초 40분 06시에 초단기 전날 DB 데이터 삭제
//    public void realTimePer40() {
//
//        RestTemplate restTemplate= new RestTemplate();
//        // GET 요청 보내기
//        String response = restTemplate.getForObject("http://localhost:8085/test", String.class);
//
//
//        // 여기서 응답을 처리하거나 로깅할 수 있습니다.
//        System.out.println("GET 요청 결과: " + response);
//    }


}
