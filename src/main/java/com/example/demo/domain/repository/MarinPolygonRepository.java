package com.example.demo.domain.repository;

import com.example.demo.domain.entity.MarinPolygon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarinPolygonRepository extends JpaRepository<MarinPolygon, Long> {
}
