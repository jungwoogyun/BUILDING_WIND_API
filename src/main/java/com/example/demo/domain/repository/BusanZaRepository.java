package com.example.demo.domain.repository;

import com.example.demo.domain.entity.BusanAir;
import com.example.demo.domain.entity.BusanZa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusanZaRepository extends JpaRepository<BusanZa,Long> {
}
