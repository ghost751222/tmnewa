package com.example.tmnewa.config;

import com.example.tmnewa.authentications.LoginAuthProvider;
import com.example.tmnewa.authentications.LoginAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Autowired
    TWNEWAConfigProperties twnewaConfigProperties;

    @Autowired
    LoginAuthProvider loginAuthProvider;

    @Autowired
    LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList(twnewaConfigProperties.getCrossSiteUrl()));
        config.setAllowedMethods(Arrays.asList("GET", "POST"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/login", config);
        source.registerCorsConfiguration("/", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/login/**"))
                .headers((headers) ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/common/login","/login/**", "/logout", "/css/**", "/js/**", "/webfonts/**", "/oauth2/authorization/azure").permitAll()
                        //.requestMatchers("/userInfo").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/common/login?logout")
                        .deleteCookies("JSESSIONID")
//                        .addLogoutHandler((request, response, authentication) -> {
//                            if ("oauth2Login".equals(request.getSession().getAttribute("loginType"))) {
//                                // 触发 Azure AD 的登出端点
//                                String logoutUrl = "https://login.microsoftonline.com/{tenant-id}/oauth2/v2.0/logout?post_logout_redirect_uri={redirect-uri}&client_id={client_id}";
//                                // 将 URL 替换为实际的 tenant-id 和 redirect-uri
//                                logoutUrl = logoutUrl.replace("{tenant-id}", twnewaConfigProperties.getAzureTenantId())
//                                        .replace("{redirect-uri}", URLEncoder.encode(twnewaConfigProperties.getAzurePostLogoutRedirectUri(), StandardCharsets.UTF_8))
//                                        .replace("{client_id}", URLEncoder.encode(twnewaConfigProperties.getAzureClientId(), StandardCharsets.UTF_8));
//                                try {
//                                    response.sendRedirect(logoutUrl); // 重定向到 Azure AD 的登出端点
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//                        })
                )
                .authenticationProvider(loginAuthProvider)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login?error=true"))
                )
                .oauth2Login((oauth2Login) -> oauth2Login.successHandler(loginAuthenticationSuccessHandler)
                )

        ;
        return http.build();
    }
}