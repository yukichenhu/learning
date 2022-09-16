package com.chenhu.learning.security;

import com.chenhu.learning.controller.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author 陈虎
 * @date 2022-06-23 16:41
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Resource
    private MyUserDetailService userDetailService;
    @Resource
    private MyCustomizeAuthenticationEntryPoint myCustomizeAuthenticationEntryPoint;
    @Resource
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Resource
    private MyAuthFailureHandler authFailureHandler;
    @Resource
    private MyAccessDeniedHandler accessDeniedHandler;
    @Resource
    private MyAccessDecisionManager myAccessDecisionManager;
    @Resource
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;
    @Resource
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager(){
        List<AccessDecisionVoter<? extends Object>> decisionVoterList= Arrays.asList(new MyAuthVoter(),new WebExpressionVoter());
        return new UnanimousBased(decisionVoterList);
    }
    @Bean
    public FilterSecurityInterceptor myFilterSecurityInterceptor(){
        MySecurityInterceptor mySecurityInterceptor=new MySecurityInterceptor();
        mySecurityInterceptor.setAccessDecisionManager(myAccessDecisionManager);
        mySecurityInterceptor.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
        return mySecurityInterceptor;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .formLogin()
//                .loginPage("/admin/login")
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authFailureHandler)
//                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myCustomizeAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailService)
                .authorizeRequests(auth-> auth.antMatchers("/**").permitAll())
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
