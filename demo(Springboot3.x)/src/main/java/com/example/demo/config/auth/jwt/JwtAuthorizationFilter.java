package com.example.demo.config.auth.jwt;


import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * JWT를 이용한 인증
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //  인증이후(ID/PW검증) 매번 페이지 요청시마다 동작시킬 필터함수
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException, IOException {
        System.out.println("[JWTAUTHORIZATIONFILTER_START] doFilterInternal...");

        String token = null;
        try {
            // cookie 에서 JWT token을 가져옵니다.
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
                    .map(cookie -> cookie.getValue())
                    .orElse(null);
            System.out.println("TOKEN : " + token );
            
            //readme로 읽어올것
            
        } catch (Exception ignored) {

        }
        if (token != null) {
            try {
                if(jwtTokenProvider.validateToken(token)) {

                    Authentication authentication = getUsernamePasswordAuthenticationToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("[JWTAUTHORIZATIONFILTER] : " + authentication);
                }
            } catch (ExpiredJwtException e)     //토큰만료시 예외처리(쿠키 제거)
            {

                System.out.println("[JWTAUTHORIZATIONFILTER_END] : ...ExpiredJwtException ...."+e.getMessage());

                //토큰 만료시 처리(Refresh-token으로 갱신처리는 안함-쿠키에서 제거)
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);

            }catch(Exception e2){
                System.out.println("[JWTAUTHORIZATIONFILTER] ...!!!!!!!!!!!!!!!!!!!!!!!!!"+e2);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * JWT 토큰으로 User를 찾아서 UsernamePasswordAuthenticationToken를 만들어서 반환한다.
     * User가 없다면 null
     */
    public Authentication getUsernamePasswordAuthenticationToken(String token) throws IOException {

        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        Optional<User> user = userRepository.findById(authentication.getName()); // 유저를 유저명으로 찾습니다.
        System.out.println("[JWTAUTHORIZATIONFILTER]  getUsernamePasswordAuthenticationToken...authentication : " +authentication );
        if(user!=null)
        {
            return authentication;
        }
        return null; // 유저가 없으면 NULL
    }

}