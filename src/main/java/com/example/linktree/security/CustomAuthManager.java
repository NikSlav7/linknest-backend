package com.example.linktree.security;

import com.example.linktree.managers.UserDetailsManager;
import com.example.linktree.security.DaoUserDetailsService;
import com.example.linktree.services.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthManager implements AuthenticationManager {


    @Autowired
    private DaoUserDetailsService service;
    private static PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder(){
        return passwordEncoder = new BCryptPasswordEncoder(10);
    }

    @Override
    public org.springframework.security.core.Authentication authenticate(org.springframework.security.core.Authentication authentication) throws AuthenticationException {
        final UserDetails userDetail = service.loadUserByUsername(authentication.getName());
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
    }
}