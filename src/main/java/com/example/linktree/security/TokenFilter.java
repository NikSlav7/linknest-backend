package com.example.linktree.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(LoginFilter.SIGNING_KEY.getBytes()))
                .build().parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        var list = (List<String>) body.get("Authorities");
        List<SimpleGrantedAuthority> grantedAuthorities = list.stream().map(SimpleGrantedAuthority::new).toList();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(body.getSubject(), null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
