package com.example.demo.domain.repository;

import com.example.demo.domain.entity.RealTimeWindDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealTimeWindDirectionRepostitory extends JpaRepository<RealTimeWindDirection,String> {
}
