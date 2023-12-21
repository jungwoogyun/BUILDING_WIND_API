package com.example.demo.domain.repository;


import com.example.demo.domain.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByAddr1LikeAndAddr2(String addr1 , String addr2);
    List<Weather> findByAddr1LikeAndAddr2AndAddr3(String addr1 , String addr2, String addr3);
    List<Weather> findByAddr1Like(String addr1);

    List<Weather> findByAddr1AndAddr2(String addr1, String addr2);



}


