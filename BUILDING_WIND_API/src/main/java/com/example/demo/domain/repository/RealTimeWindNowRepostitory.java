package com.example.demo.domain.repository;

import com.example.demo.domain.entity.RealTimeWindDirection;
import com.example.demo.domain.entity.RealTimeWindNow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealTimeWindNowRepostitory extends JpaRepository<RealTimeWindNow,String> {
}
