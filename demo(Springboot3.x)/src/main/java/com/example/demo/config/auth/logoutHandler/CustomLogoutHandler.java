package com.example.demo.config.auth.logoutHandler;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.config.auth.jwt.JwtProperties;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.domain.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;



public class CustomLogoutHandler implements LogoutHandler{

	private RestTemplate restTemplate;


	//KAKAO----------------------------------------------------------------
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoCliendId;

	private String LOGOUT_REDIRECT_URI = "http://localhost:8080/login";
	//KAKAO----------------------------------------------------------------
	//NAVER----------------------------------------------------------------
	//@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String naverClientId="9EBQUS2DbhHgnSzLZyx9";

	//@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String naverClientSecret="QBZ4lfUe9D";
	//NAVER----------------------------------------------------------------

	//JWT
	private JwtTokenProvider jwtTokenProvider;
	//REMEMBER ME
	private PersistentTokenRepository persistentTokenRepository;
	public CustomLogoutHandler(JwtTokenProvider jwtTokenProvider,PersistentTokenRepository persistentTokenRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.restTemplate = new RestTemplate();
		this.persistentTokenRepository = persistentTokenRepository;

	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {


		//JWT START----------------------------------------------------------------

		String token = null;
		try {
			// cookie 에서 JWT token을 가져옵니다.
			token = Arrays.stream(request.getCookies())
					.filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
					.map(cookie -> cookie.getValue())
					.orElse(null);
			System.out.println("[CUSTOMLOGOUTHANDLER] Access TOKEN : " + token );
		} catch (Exception ignored) {

		}
		Authentication authentication = null;
		try {
			authentication = jwtTokenProvider.getAuthentication(token);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("[CUSTOMLOGOUTHANDLER] authentication : " + authentication);
		System.out.println("[CUSTOMLOGOUTHANDLER] authentication Details : " + authentication.getDetails());

		//JWT END-----------------------------------------------------------


		//REMEMBER ME REMOVE START------------------------------------------
		System.out.println("[CUSTOMLOGOUTHANDLER] persistentTokenRepository :"+ persistentTokenRepository);
		persistentTokenRepository.removeUserTokens(authentication.getName());

		//REMEMBER ME REMOVE END  ------------------------------------------


		HttpSession session = request.getSession(false);
		if(session!=null)
			session.invalidate();

		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("[CUSTOMLOGOUTHANDLER] principalDetails : " + principalDetails);


		String provider = principalDetails.getUser().getProvider();


		if(provider!=null&&StringUtils.contains(provider,"kakao")){

			//--------------------------------
			//01 KAKAO LOGOUT
			//--------------------------------
			System.out.println("카카오 로그아웃 진행!...................");

			String accessToken = principalDetails.getAccessToken();

			//URL
			String url = "https://kapi.kakao.com/v1/user/logout";
			//Header
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			headers.add("Authorization", "Bearer "+accessToken);
			//Parameter
			//header + parameter
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
			//Request_Case1
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> resp =  rt.exchange(url, HttpMethod.POST,entity,String.class);

			System.out.println(resp);
			System.out.println(resp.getBody());

		}
		else if(provider!=null&&StringUtils.contains(provider,"google")){

			String accessToken = principalDetails.getAccessToken();

			String url ="https://accounts.google.com/o/oauth2/revoke?token="+accessToken;
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(url,HttpMethod.GET,null,String.class);

		}
		else if(provider!=null&&StringUtils.contains(provider,"naver"))
		{

			//https://developers.naver.com/docs/login/devguide/devguide.md#5-3-1-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%97%B0%EB%8F%99-%ED%95%B4%EC%A0%9C%EA%B0%80-%ED%95%84%EC%9A%94%ED%95%9C-%EA%B2%BD%EC%9A%B0

			String accessToken = principalDetails.getAccessToken();

			System.out.println("[LOGOUT_HANDLER] naverClientId : " + naverClientId);
			System.out.println("[LOGOUT_HANDLER] naverClientSecret : " + naverClientSecret);

			System.out.println("[LOGOUT_HANDLER] authentication  : "  + authentication);
			System.out.println("[LOGOUT_HANDLER] authentication getPrincipal()  : "  + (PrincipalDetails)authentication.getPrincipal());
			System.out.println("[LOGOUT_HANDLER] getAccessToken()  : "  + ((PrincipalDetails) authentication.getPrincipal()).getAccessToken());


			// 네이버 API 로그아웃을 위한 URL 생성
			String logoutUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="
					+ naverClientId + "&client_secret=" + naverClientSecret + "&access_token=" + accessToken+"&service_provider=NAVER";



			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(logoutUrl, HttpMethod.GET, entity, String.class);

		}




	}


}
