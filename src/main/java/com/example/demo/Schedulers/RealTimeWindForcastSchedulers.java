package com.example.demo.Schedulers;


import com.example.demo.domain.entity.RealTimeError;
import com.example.demo.domain.entity.RealTimeForcastNow;
import com.example.demo.domain.repository.RealTimeErrorRepository;
import com.example.demo.domain.repository.RealTimeForcastNowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class RealTimeWindForcastSchedulers {

    private Long idx;
    private RestTemplate restTemplate;

    public RealTimeWindForcastSchedulers(){
        restTemplate = new RestTemplate();
        idx = 0L;
    }


    @Autowired
    private RealTimeForcastNowRepository realTimeForcastNowRepository;

    @Autowired
    private RealTimeErrorRepository realTimeErrorRepository;


    // @Scheduled(cron = "0 * * * * *")	//TEST
    // 50분마다 실행  : cron = "0 */50 * * * *
    // 06 50
    //@Scheduled(cron = "0 40 * * * *")
    //@Scheduled(cron = "0 22,42 6-23/1 * * *")     //6-23/1은 6부터 23까지의 모든 시간 값을 1씩 증가 + 매시 20분 40분 마다
    @Scheduled(cron = "0 */5 * * * *")
    public void PerDay() throws InterruptedException {

        try {

            // GET 요청 보내기
            String response = restTemplate.getForObject("http://localhost:8085/RT_FORCAST_NOW", String.class);

            // 여기서 응답을 처리하거나 로깅할 수 있습니다.
            //System.out.println("GET 요청 결과: " + response);
        }catch(Exception e){


            System.out.println("RealTimeWindForcastSchedulers ERROR_"+(idx++)+" : " + e.getMessage());
            System.out.println("80초 후다시 진행..");
            realTimeErrorRepository.save(new RealTimeError(LocalDateTime.now(), "RT_FORCAST_ERROR_"+(idx++), e.getMessage()));
            Thread.sleep(1000*80);try {
                String response = restTemplate.getForObject("http://localhost:8085/RT_FORCAST_NOW", String.class);
            }catch(Exception e1){

                System.out.println("RealTimeWindForcastSchedulers ERROR_"+(idx++)+" : " + e.getMessage() );

                realTimeErrorRepository.save(new RealTimeError(LocalDateTime.now(), "RT_FORCAST_ERROR"+(idx++), e.getMessage()));

            }
        }
    }


}
