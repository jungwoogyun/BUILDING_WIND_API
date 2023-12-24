package com.example.demo.domain.repository;

import com.example.demo.domain.entity.RealTimeForcastNow;
import com.example.demo.domain.entity.RealTimeWindDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealTimeForcastNowRepository extends JpaRepository<RealTimeForcastNow,String> {

    @Query("SELECT r FROM RealTimeForcastNow r WHERE r.fcstDate = :fcstDate and r.fcstTime = :fcstTime")
    List<RealTimeForcastNow>  findByBaseDateAndBaseTime(@Param("fcstDate") String fcstDate, @Param("fcstTime") String fcstTime);
//
//
//    @Modifying
//    @Query("DELETE FROM RealTimeWindDirection r WHERE r.baseDate = :baseDate and r.baseTime = :baseTime")
//    void deleteAllBaseDateAndBaseTime(@Param("baseDate") String baseDate, @Param("baseTime") String baseTime);


}
