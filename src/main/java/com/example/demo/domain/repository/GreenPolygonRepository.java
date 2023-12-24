package com.example.demo.domain.repository;

import com.example.demo.domain.entity.GreenPolygon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenPolygonRepository extends JpaRepository<GreenPolygon, Long> {
}
