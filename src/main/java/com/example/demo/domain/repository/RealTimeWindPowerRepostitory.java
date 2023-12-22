package com.example.demo.domain.repository;

import com.example.demo.domain.entity.RealTimeWindPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealTimeWindPowerRepostitory extends JpaRepository<RealTimeWindPower,String> {
}
