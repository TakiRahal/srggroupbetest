package com.takirahal.srfgroup.config;


import com.takirahal.srfgroup.security.JwtAuthenticationEntryPoint;
import com.takirahal.srfgroup.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // For user principal.id inside repository
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors()
            .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
            .and()
                .authorizeHttpRequests()
                .requestMatchers(
                            "/favicon.ico",
                            "/*/*.png",
                            "/*/*.gif",
                            "/*/*.svg",
                            "/*/*.jpg",
                            "/*/*.html",
                            "/*/*.css",
                            "/*/*.js",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/webjars/**",
                            "/api/management/**",
                            "/api/cgu/public/**",
                            "/api/customer/**",
                            "/api/user/public/**",
                            "/api/offer/public/**", "/api/find-offer/public/**", "/api/sell-offer/public/**","/api/advertising-per-period/public/**",
                            "/api/rent-offer/public/**", "/api/address/public/**", "/api/category/public/**",
                            "/api/faq/public/**", "/api/article/public/**", "/api/news-letter/public/**", "/api/description-add-offers/public/**",
                            "/api/post/public/**", "/api/suggest-search/**", "/api/monitoring/**", "/api/aboutus/public/**",
                            "/api/contactus/public/**", "/api/post-home-feature/public/**", "/api/top-home-slides-images/public/**",
                            "/api/offer-images/public/**", "/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability").permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
