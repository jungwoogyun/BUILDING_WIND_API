package com.example.demo.domain.repository;

import com.example.demo.domain.entity.RealTimeError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface RealTimeErrorRepository  extends JpaRepository<RealTimeError, LocalDateTime> {
}
