package com.example.demo.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;




@Entity
@Data
public class Weather {

    @Id
    @Column
    private Long code;

    @Column
    private String addr1;

    @Column
    private String addr2;
    @Column
    private String addr3;
    @Column
    private Integer nx;
    @Column
    private Integer ny;


}