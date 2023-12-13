package com.example.demo.config.auth.logoutHandler;


import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.config.auth.jwt.JwtAuthorizationFilter;
import com.example.demo.config.auth.jwt.JwtProperties;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;


public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	//KAKAO----------------------------------------------------------------
	private String kakaoCliendId = "ef4f3d1c4e61aa5c2695db6b57320f9a";		//ADMIN KEY

	private String LOGOUT_REDIRECT_URI = "http://localhost:8080/login";
	//KAKAO----------------------------------------------------------------
	//NAVER----------------------------------------------------------------
	//@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String naverClientId="9EBQUS2DbhHgnSzLZyx9";

	//@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String naverClientSecret="QBZ4lfUe9D";
	//NAVER----------------------------------------------------------------



	//JWT
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	public CustomLogoutSuccessHandler(JwtTokenProvider jwtTokenProvider) {

		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		//JWT START----------------------------------------------------------------

		String token = null;
		try {
			// cookie 에서 JWT token을 가져옵니다.
			token = Arrays.stream(request.getCookies())
					.filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
					.map(cookie -> cookie.getValue())
					.orElse(null);
			System.out.println("TOKEN : " + token );
		} catch (Exception ignored) {

		}
		Authentication authentication =  jwtTokenProvider.getAuthentication(token);
		System.out.println("CustomLogoutHandler's logout authentication : " + authentication);

		//JWT END-----------------------------------------------------------

		System.out.println("CustomLogoutSuccessHandler's onLogoutSuccess! authentication : " + authentication);

		PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();
		String provider = principalDetails.getUser().getProvider();

		if(provider!=null&&StringUtils.contains(provider,"kakao")){
			String url2 = "https://kauth.kakao.com/oauth/logout?client_id="+kakaoCliendId+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;
			response.sendRedirect(url2);
			return ;
		}
		else if(provider!=null&&StringUtils.contains(provider,"naver"))
		{
			String url = "http://nid.naver.com/nidlogin.logout"; //네이버 APP 연결끊기
			response.sendRedirect(url);
			return ;
		}
		else if(provider!=null&&StringUtils.contains(provider,"google"))
		{
			String url = "https://accounts.google.com/Logout";
			response.sendRedirect(url);
			return;
		}

		response.sendRedirect( request.getContextPath() );	// '/' 경로로 이동

	}

}
