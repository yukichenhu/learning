package com.chenhu.learning.security;

import com.alibaba.fastjson.JSON;
import com.chenhu.learning.utils.JwtUtils;
import com.chenhu.learning.vo.UserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈虎
 * @date 2022-06-24 10:46
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //登录成功的处理
        UserDetail userDetail=(UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token= JwtUtils.getJwtToken(userDetail.getId(),userDetail.getName());
        Map<String,Object> map=new HashMap<>(1);
        map.put("token",token);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(map));
    }
}
