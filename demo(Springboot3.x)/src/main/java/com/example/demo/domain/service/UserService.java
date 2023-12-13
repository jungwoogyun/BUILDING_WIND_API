package com.example.demo.domain.service;


import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.type.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public boolean joinMember(UserDto dto, Model model, HttpServletRequest request)
    {

        //패스워드 일치여부확인
        if(!dto.getPassword().equals(dto.getRepassword()))
        {
            model.addAttribute("repassword","패스워드가 일치하지 않습니다");
            return false;
        }

        //DTO -> ENTITY
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setPhone(dto.getPhone());
        user.setZipcode(dto.getZipcode());
        user.setAddr1(dto.getAddr1());
        user.setAddr2(dto.getAddr2());
        user.setProvider(dto.getProvider());
        user.setProviderId(dto.getProviderId());

        //DB SAVE
        userRepository.save(user);
        return true;

    }

}
