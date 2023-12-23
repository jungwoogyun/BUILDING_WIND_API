package com.example.demo.Schedulers;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RealTimeWindPowerAndDirectionSchedulers {
    private RestTemplate restTemplate;

    public RealTimeWindPowerAndDirectionSchedulers(){
        restTemplate = new RestTemplate();
    }

   // @Scheduled(cron = "0 * * * * *")	//TEST
   @Scheduled(cron = "0 0 */50 * * *")	//50분마다 실행
    public void PerDay() throws InterruptedException {

        try {

            // GET 요청 보내기
            String response = restTemplate.getForObject("http://localhost:8085/RTNOW", String.class);

            // 여기서 응답을 처리하거나 로깅할 수 있습니다.
            //System.out.println("GET 요청 결과: " + response);
        }catch(Exception e){


            System.out.println("RealTimeWindPowerAndDirectionSchedulers ERROR : " + e.getMessage());
            System.out.println("20초 후다시 진행..");
            Thread.sleep(20000);try {
                String response = restTemplate.getForObject("http://localhost:8085/RTNOW", String.class);
            }catch(Exception e1){
                System.out.println("RealTimeWindPowerAndDirectionSchedulers ERROR_2 :" + e.getMessage() );
                System.out.println("ERROR DB 저장하고 계속 스케쥴링.. 지금은 DB저장 생략" );
            }


        }
    }

}

