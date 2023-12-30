package com.example.demo.domain.repository;

import com.example.demo.domain.entity.BusanAir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusanAirRepository extends JpaRepository<BusanAir,Long> {
}
