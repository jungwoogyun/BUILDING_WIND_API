package com.example.demo.domain.entity;

import com.example.demo.type.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="user")
public class User {

    @Id
    private String username;
    private String password;
    private Role role;
    private String phone;
    private String zipcode;
    private String addr1;
    private String addr2;

    // OAuth2 Added
    private String provider;
    private String providerId;



}
