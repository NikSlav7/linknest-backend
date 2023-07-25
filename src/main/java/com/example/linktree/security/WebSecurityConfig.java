package com.example.linktree.security;

import com.example.linktree.repo.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.example.linktree.security")
public class WebSecurityConfig {

    private final UserRepository userRepository;

    private AuthenticationManager authenticationManager;




    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    @DependsOn("authenticationManager")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationConfiguration configuration) throws Exception {
        httpSecurity.authorizeHttpRequests(http ->
                http.requestMatchers("/login", "/api/details/register", "/api/linknest/get-share").permitAll().anyRequest().authenticated());
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.formLogin(login -> login.successForwardUrl("/"));
        httpSecurity.cors(cors -> cors.configure(httpSecurity));
        LoginFilter loginFilter = new LoginFilter(authenticationManager);
        loginFilter.setFilterProcessesUrl("/login");
        httpSecurity.addFilter(loginFilter).addFilterAfter(new TokenFilter(), LoginFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new DaoUserDetailsService(userRepository);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*");
            }
        };
    }

    @Bean
    @Primary
    AuthenticationManager authenticationManager(){
        return authenticationManager = new CustomAuthManager();
    }

}
