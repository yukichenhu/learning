package com.chenhu.learning.security;

import com.chenhu.learning.controller.MyUserDetailService;
import com.chenhu.learning.utils.AnnotationUtil;
import com.chenhu.learning.utils.JwtUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 陈虎
 * @date 2022-06-24 11:00
 */
@Component
@Lazy
public class JwtFilter extends OncePerRequestFilter {
    @Resource
    private MyUserDetailService myUserDetailService;

    private List<String> authUrls;

    @Resource
    private AnnotationUtil annotationUtil;

    @PostConstruct
    private void init(){
        authUrls=annotationUtil.getAnnotationRequestUrls();
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if(!authUrls.contains(request.getServletPath())){
            filterChain.doFilter(request,response);
            return;
        }
        //校验token
        if(!JwtUtils.checkToken(request)){
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("token无效，请重新登录");
            return;
        }
        String username= JwtUtils.getUsername(request);
        if(!ObjectUtils.isEmpty(username)){
            UserDetails userDetails=myUserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //放行
        filterChain.doFilter(request,response);
    }


}
