package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//https://cloudstudying.kr/lectures/252

@Component
public class ScheduledTask {

    private final String targetUrl = "http://localhost:8085/realTime"; //
//      @Scheduled(cron = "0 20 9 * * *") // 매일 09시 실행
//    @Scheduled(cron = "0 15 10 * * *") // 매일 10시 15분 실행
//    @Scheduled(cron = "0 * 14 * * *") // 매일 14시에 0분~59분까지 매분 실행
//    @Scheduled(cron = "0 0/5 14 * * *") // 매일 14시에 시작해서 5분 간격으로 실행
//    @Scheduled(cron = "0 0/5 14,18 * * *") // 매일 14시, 18시에 시작해서 5분 간격으로 실행
//    @Scheduled(cron = "0 0-5 14 * * *") // 매일 14시에 0분, 1분, 2분, 3분, 4분, 5분에 실행
//    @Scheduled(cron = "0 0 20 ? * MON-FRI") // 월~금일 20시 0분 0초에 실행
//    @Scheduled(cron = "0 0/5 14 * * ?") // 아무요일, 매월, 매일 14:00부터 14:05분까지 매분 0초 실행 (6번 실행됨)
//    @Scheduled(cron = "0 15 10 ? * 6L") // 매월 마지막 금요일 아무날이나 10:15:00에 실행
//    @Scheduled(cron = "0 15 10 15 * ?") // 아무요일, 매월 15일 10:15:00에 실행
//    @Scheduled(cron = "* /1 * * * *") // 매 1분마다 실행
    @Scheduled(cron = "* */30 * * * *") // 매 10분마다 실행
    public void executeTask() {
        RestTemplate restTemplate= new RestTemplate();
        // GET 요청 보내기
        String response = restTemplate.getForObject(targetUrl, String.class);

        // 여기서 응답을 처리하거나 로깅할 수 있습니다.
        System.out.println("GET 요청 결과: " + response);
    }
}
