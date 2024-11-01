package com.example.ecommerce.security;

import com.example.ecommerce.security.jwt.AuthJwtTokenFilter;
import com.example.ecommerce.security.jwt.JwtAuthEntryPoint;
import com.example.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/java/com/example/ecommerce/uploads/";

    private static final List<String> SECURED_URLS = List.of("/api/v1/carts/**", "/api/v1/cartItems/**");


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private final UserService userService;
    private final JwtAuthEntryPoint authEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthJwtTokenFilter authTokenFilter(){
        return new AuthJwtTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                                .anyRequest().permitAll())
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
