package com.example.demo.domain.dto;

import lombok.Data;

@Data
public class obsrValue {

    String T1H; //기온		℃	10
    String RN1;	//1시간 강수량	mm	8
    String UUU;	//동서바람성분	m/s	12
    String VVV;	//남북바람성분	m/s	12
    String REH;	//습도		%	8
    String PTY;	//강수형태	코드값	4
    String VEC;	//풍향		deg	10
    String WSD;	//풍속		m/s	10

}
