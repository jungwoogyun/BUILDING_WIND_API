package com.example.demo.config.auth.loginHandler;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.config.auth.jwt.JwtProperties;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.config.auth.jwt.TokenInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {


    private JwtTokenProvider jwtTokenProvider;

    public CustomLoginSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //----------------------------------------
        //OAUTH2 Login JWT 생성 위한 코드
        //----------------------------------------
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        String token = JwtUtils.createToken(principalDetails);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        System.out.println("TOKEN : " + tokenInfo );
        // 쿠키 생성
        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, tokenInfo.getAccessToken());
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키의 만료시간 설정
        cookie.setPath("/");
        response.addCookie(cookie);
        //----------------------------------------

        System.out.println("[CUSTOMLOGINSUCCESSHANDLER] onAuthenticationSuccess! ");
        Collection<? extends GrantedAuthority> collection =   authentication.getAuthorities();

        //----------------------------------------

//        collection.forEach((role)->{
//            try {
//                System.out.println("role : " + role.getAuthority());
//                String role_str = role.getAuthority();
//                if(role_str.equals("ROLE_USER")) {
//
//                    System.out.println("USER 페이지로 이동!");
//                    response.sendRedirect(request.getContextPath()+"/user");
//                    return ;
//                }else if(role_str.equals("ROLE_MEMBER")){
//                    System.out.println("MEMBER 페이지로 이동!");
//                    response.sendRedirect(request.getContextPath()+"/member");
//                    return ;
//                }
//                else if(role_str.equals("ROLE_ADMIN")) {
//                    System.out.println("ADMIN 페이지로 이동!");
//                    response.sendRedirect(request.getContextPath()+"/admin");
//                    return ;
//                }
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//
//        } );
        response.sendRedirect("/");

    }
}
