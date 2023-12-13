package com.example.demo.config.auth;


import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.config.auth.provider.GoogleUserInfo;
import com.example.demo.config.auth.provider.KakaoUserInfo;
import com.example.demo.config.auth.provider.NaverUserInfo;
import com.example.demo.config.auth.provider.OAuth2UserInfo;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class PrincipalDetailsOAuth2Service  extends DefaultOAuth2UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    public PrincipalDetailsOAuth2Service(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepository;


    //JWT
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //OAUTH2 로그인 검증
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("[OAUTH2 Service] loadUser CALL!!");
        System.out.println("[OAUTH2 Service]userRequest : "+ userRequest);
        System.out.println("[OAUTH2 Service]userRequest.getClientRegistration() : "+ userRequest.getClientRegistration());
        System.out.println("[OAUTH2 Service]userRequest.getAccessToken() : "+ userRequest.getAccessToken());
        System.out.println("[OAUTH2 Service]userRequest.getAdditionalParameters() : "+ userRequest.getAdditionalParameters());

        System.out.println("[OAUTH2 Service]getAccessToken().getTokenValue() : "+ userRequest.getAccessToken().getTokenValue());
        System.out.println("[OAUTH2 Service]getAccessToken().getTokenType() : "+ userRequest.getAccessToken().getTokenType().getValue());
        System.out.println("[OAUTH2 Service]getAccessToken().getScopes() : "+ userRequest.getAccessToken().getScopes());

        //Attribute 확인
        OAuth2User oauth2User =   super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        System.out.println("auth2User.getAttributes() : " +oauth2User.getAttributes());


        if(userRequest.getClientRegistration().getRegistrationId().equals("kakao"))
        {
            System.out.println("[] 카카오 로그인");
            KakaoUserInfo kakaoUserInfo = new KakaoUserInfo((Map<String, Object>) oauth2User.getAttributes().get("properties"));
            kakaoUserInfo.setId(userRequest.getClientRegistration().getClientId());
            oAuth2UserInfo = kakaoUserInfo;
            //oAuth2UserInfo = new KakaoUserInfo((Map<String, Object>) oauth2User.getAttributes().get("properties"));
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("google"))
        {
            System.out.println("[] 구글 로그인");
            oAuth2UserInfo  = new GoogleUserInfo(oauth2User.getAttributes());

        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("naver"))
        {
            System.out.println("[] 네이버 로그인");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oauth2User.getAttributes().get("response"));
        }

        //OAuth2UserInfo 확인
        String provider  = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;    //  /
        String password = passwordEncoder.encode("1234");
        String email = oAuth2UserInfo.getEmail();
        Role role = Role.ROLE_USER;

        //DB 저장
        Optional<User> optional =   userRepository.findById(username);
        if(optional.isEmpty()) {
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
            System.out.println("[OAUTH] "+provider +" 최초 로그인 요청!");
        }else{
            System.out.println("[OAUTH] 기존 계정"+optional.get().getUsername() +"으로 로그인!");
        }


        //AccessToken 정보를 Authentication에 저장하기

        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setAttributes(oauth2User.getAttributes());
        UserDto dto = new UserDto();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setProvider(provider);  //JWT ADDED..

        if(role.ordinal()==0){
            dto.setRole("ROLE_USER");
        }else if(role.ordinal()==1){
            dto.setRole("ROLE_ADMIN");
        }else{
            dto.setRole("ROLE_USER");
        }


        //
        dto.setProvider(provider);
        dto.setProviderId(providerId);
        //
        principalDetails.setUser(dto);
        principalDetails.setAccessToken(userRequest.getAccessToken().getTokenValue());


        return principalDetails;

    }

    //----------------------------------------------------------------
    // JWT
    //----------------------------------------------------------------
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.demo.domain.entity.User> user =  userRepository.findById(username);

        UserDto dto = new UserDto();
        dto.setUsername(user.get().getUsername());
        dto.setPassword(user.get().getPassword());
        Role role = user.get().getRole();

        if(role.ordinal()==0){
            dto.setRole(("ROLE_USER"));
        }else if(role.ordinal()==1){
            dto.setRole("ROLE_ADMIN");
        }else{
            dto.setRole("ROLE_USER");
        }

        dto.setProvider(user.get().getProvider());  //JWT ADDED..
        if(dto==null)
            return null;

        return new PrincipalDetails(dto);

        //		if(user.isEmpty())
//			return null;
//		return new PrincipalDetails(user.get());
    }
}
