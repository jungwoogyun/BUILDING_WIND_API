package com.example.demo.controller;


import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.service.UserService;
import com.example.demo.type.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;



    @GetMapping("/user")
    public void user(Authentication authentication, Model model) {
        log.info("GET /user...");
        System.out.println("authentication : " + authentication);
        System.out.println("name : " + authentication.getName());
        System.out.println("principal : " + authentication.getPrincipal());
        System.out.println("authorities : " + authentication.getAuthorities());
        System.out.println("detail : " + authentication.getDetails());
        System.out.println("credential : " + authentication.getCredentials());

        model.addAttribute("authentication", authentication);
    }



    @GetMapping("/join")
    public void join_get() {
        log.info("GET /user/join");
    }

    @PostMapping("/join")
    public String join_post(@Valid UserDto dto, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) {
        log.info("POST /join "+dto);

        //01

        //02
        if(bindingResult.hasFieldErrors()) {
            for( FieldError error  : bindingResult.getFieldErrors()) {
                log.info(error.getField()+ " : " + error.getDefaultMessage());
                model.addAttribute(error.getField(), error.getDefaultMessage());

            }
            return "user/join";
        }

        //03
        boolean isjoin =  userService.joinMember(dto,model,request);
        if(!isjoin){
            return "user/join";
        }

        //04
        return "redirect:/login?msg=Join_Success!";

    }


    //----------------------------------
    //ID찾기  - 등록된 휴대전화 인증이후  연락처로 이메일전송(Naver SMS API)
    //----------------------------------
    @GetMapping("/findId")
    public void findId(){
        log.info("GET /user/findId...");
    }



    //----------------------------------
    //PW찾기 - ID받고
    //----------------------------------
    @GetMapping("/findPw")
    public void findPw(){
        log.info("GET /user/findPw...");
    }


}
