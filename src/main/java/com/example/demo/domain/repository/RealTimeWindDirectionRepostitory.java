package com.example.demo.domain.repository;

import com.example.demo.domain.entity.RealTimeWindDirection;
import com.example.demo.domain.entity.RealTimeWindPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.service.annotation.DeleteExchange;

import java.util.List;

@Repository
public interface RealTimeWindDirectionRepostitory extends JpaRepository<RealTimeWindDirection,String> {

    @Query("SELECT r FROM RealTimeWindDirection r WHERE r.baseDate = :baseDate and r.baseTime = :baseTime")
    List<RealTimeWindDirection> findByBaseDateAndBaseTime(@Param("baseDate") String baseDate, @Param("baseTime") String baseTime);


    @Modifying
    @Query("DELETE FROM RealTimeWindDirection r WHERE r.baseDate = :baseDate and r.baseTime = :baseTime")
    void deleteAllBaseDateAndBaseTime(@Param("baseDate") String baseDate, @Param("baseTime") String baseTime);


}
