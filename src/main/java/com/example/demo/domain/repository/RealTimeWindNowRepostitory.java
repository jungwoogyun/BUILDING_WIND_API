package com.example.demo.domain.repository;

import com.example.demo.domain.entity.RealTimeWindDirection;
import com.example.demo.domain.entity.RealTimeWindNow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RealTimeWindNowRepostitory extends JpaRepository<RealTimeWindNow,String> {

    @Modifying
    @Query("DELETE FROM RealTimeWindNow r WHERE r.baseDate = :baseDate and r.baseTime = :baseTime")
    void deleteAllBaseDateAndBaseTime(@Param("baseDate") String baseDate, @Param("baseTime") String baseTime);

}
