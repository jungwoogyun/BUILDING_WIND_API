package com.example.demo.config.auth.jwt;

import java.security.SecureRandom;

public class KeyGenerator {

    public static  byte[]  getKeygen(){
        SecureRandom secureRandom = new SecureRandom();             // 안전한 난수를 생성하기 위한 객체를 생성. 보안적으로 안전한 무작위 비트를 생성하기 위해 사용
        byte[] keyBytes = new byte[256 / 8];                        // 256비트 키 생성
        secureRandom.nextBytes(keyBytes);                           // 난수로 바이트 배열 생성
        System.out.println("KeyGenerator getKeygen Key: " + keyBytes);
        return keyBytes;
    }
}
